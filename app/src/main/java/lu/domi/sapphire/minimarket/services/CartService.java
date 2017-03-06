package lu.domi.sapphire.minimarket.services;


import android.content.Context;
import android.util.SparseArray;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.handler.SharedPreferencesHandler;

public class CartService {

    private SharedPreferencesHandler prefsHandler;
    private SparseArray<CartEntry> cartEntries;

    public CartService(Context context) {
        prefsHandler = new SharedPreferencesHandler(context);
    }

    void updateEntry(int artNo, int quantity) {
        final CartEntry entry = cartEntries.get(artNo);
        entry.updateQuanity(quantity);
        if (entry.getQuanity() < 1) {
            removeEntry(artNo);
            getPrefsHandler().delete(artNo);
        } else {
            getPrefsHandler().insertUpdate(entry, artNo);
        }
    }

    public void insertEntry(Product product, int quantity) {
        CartEntry newEntry = new CartEntry(product.getName(), product.getPrice(), quantity);
        getCartEntries().put(product.getArtNo(), newEntry);
        getPrefsHandler().insertUpdate(newEntry, product.getArtNo());
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        BigDecimal rowTotal;
        for(int i = 0; i < getCartEntries().size(); i++) {
            int key = getCartEntries().keyAt(i);
            rowTotal = getRowTotalOf(key);
            subtotal = subtotal.add(rowTotal);
        }
        return subtotal;
    }

    private BigDecimal getRowTotalOf(int key) {
        CartEntry entry = cartEntries.get(key);
        BigDecimal quantity = new BigDecimal(entry.getQuanity());
        return entry.getBasePrice().multiply(quantity);
    }

    public void cleanUpCart() {
        cartEntries = new SparseArray<>();
        getPrefsHandler().deleteAll();
    }

    public String getFormatedRowTotalOf(int key, BigDecimal rate) {
        BigDecimal rowTotal= getRowTotalOf(key).multiply(rate);
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(rowTotal);
    }

    boolean contains(int artNo) {
        return getCartEntries().get(artNo) != null;
    }

    CartEntry getCartEntry(int artNo) {
        return cartEntries.get(artNo);
    }

    public SparseArray<CartEntry> getCartEntries() {
        if (cartEntries == null) {
            cartEntries = getPrefsHandler().loadAll();
        }
        return cartEntries;
    }

    void removeEntry(int artNr) {
        cartEntries.remove(artNr);
        getPrefsHandler().delete(artNr);
    }

    public SharedPreferencesHandler getPrefsHandler() {
        return prefsHandler;
    }
}
