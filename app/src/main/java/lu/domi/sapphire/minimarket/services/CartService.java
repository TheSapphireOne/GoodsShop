package lu.domi.sapphire.minimarket.services;


import android.util.SparseArray;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.Product;

public class CartService {

    private SparseArray<CartEntry> cartEntries;

    public CartService(SparseArray<CartEntry> cartEntries) {
        if (cartEntries != null) {
            this.cartEntries = cartEntries;
        } else {
            this.cartEntries = new SparseArray<>(); // TODO do it when loading data
        }
    }

    public CartService() {
        cartEntries = new SparseArray<>();
    }

    protected void updateEntry(int artNo, int quantity) {
        final CartEntry entry = cartEntries.get(artNo);
        entry.updateQuanity(quantity);
        if (entry.getQuanity() < 1) {
            removeEntry(artNo);
        }
    }

    protected void insertEntry(Product product, int quantity) {
        CartEntry newEntry = new CartEntry(product.getName(), product.getUnit(), product.getPrice(), quantity);
        cartEntries.put(product.getArtNo(), newEntry);
    }

    public String getSubtotal() {
        BigDecimal subtotal = new BigDecimal(0);
        BigDecimal rowTotal;
        for(int i = 0; i < cartEntries.size(); i++) {
            int key = cartEntries.keyAt(i);
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
        for(int i = 0; i < cartEntries.size(); i++) {
            int key = cartEntries.keyAt(i);
            rowTotal = getRowTotalOf(key);
            subtotal = subtotal.add(rowTotal);
        }
        return NumberFormat.getCurrencyInstance(locale).format(subtotal);
    }

    public String getFormatedRowTotalOf(int key, Locale locale) {
        BigDecimal rowTotal = getRowTotalOf(key);
        return NumberFormat.getCurrencyInstance(locale).format(rowTotal);
    }

    public boolean cartContains(int artNo) {
        return cartEntries.get(artNo) != null;
    }

    public CartEntry getCartEntry(int artNo) {
        return cartEntries.get(artNo);
    }

    public SparseArray<CartEntry> getCartEntries() {
        return cartEntries;
    }

    void removeEntry(int artNr) {
        cartEntries.remove(artNr);
    }
}
