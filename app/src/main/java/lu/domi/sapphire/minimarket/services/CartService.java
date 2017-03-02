package lu.domi.sapphire.minimarket.services;


import android.content.Context;
import android.util.SparseArray;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.handler.SharedPreferencesHandler;

public class CartService {

    private SharedPreferencesHandler prefsHandler;
    private SparseArray<CartEntry> cartEntries;

    public CartService(Context context) {
        prefsHandler = new SharedPreferencesHandler(context);
    }

    protected void updateEntry(int artNo, int quantity) {
        final CartEntry entry = cartEntries.get(artNo);
        entry.updateQuanity(quantity);
        if (entry.getQuanity() < 1) {
            removeEntry(artNo);
            prefsHandler.delete(artNo);
        } else {
            prefsHandler.insertUpdate(entry, artNo);
        }
    }

    protected void insertEntry(Product product, int quantity) {
        CartEntry newEntry = new CartEntry(product.getName(), product.getPrice(), quantity);
        getCartEntries().put(product.getArtNo(), newEntry);
        prefsHandler.insertUpdate(newEntry, product.getArtNo());
    }

    public String getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        BigDecimal rowTotal;
        for(int i = 0; i < getCartEntries().size(); i++) {
            int key = getCartEntries().keyAt(i);
            rowTotal = getRowTotalOf(key);
            subtotal = subtotal.add(rowTotal);
        }
        return NumberFormat.getCurrencyInstance(Locale.US).format(subtotal);
    }

    private BigDecimal getRowTotalOf(int key) {
        CartEntry entry = cartEntries.get(key);
        BigDecimal quantity = new BigDecimal(entry.getQuanity());
        return entry.getBasePrice().multiply(quantity);
    }

    public String getFormatedSubtotal(Locale locale) {
        BigDecimal subtotal = new BigDecimal(0);
        BigDecimal rowTotal;
        for(int i = 0; i < getCartEntries().size(); i++) {
            int key = getCartEntries().keyAt(i);
            rowTotal = getRowTotalOf(key);
            subtotal = subtotal.add(rowTotal);
        }
        return NumberFormat.getCurrencyInstance(locale).format(subtotal);
    }

    public void cleanUpCart() {
        cartEntries = new SparseArray<>();
        prefsHandler.deleteAll();
    }

    public String getFormatedRowTotalOf(int key, Locale locale) {
        BigDecimal rowTotal = getRowTotalOf(key);
        return NumberFormat.getCurrencyInstance(locale).format(rowTotal);
    }

    public boolean cartContains(int artNo) {
        return getCartEntries().get(artNo) != null;
    }

    public CartEntry getCartEntry(int artNo) {
        return cartEntries.get(artNo);
    }

    public SparseArray<CartEntry> getCartEntries() {
        if (cartEntries == null) {
            // Load cart
            cartEntries = prefsHandler.loadAll();
        }
        return cartEntries;
    }

    void removeEntry(int artNr) {
        cartEntries.remove(artNr);
        prefsHandler.delete(artNr);
    }
}
