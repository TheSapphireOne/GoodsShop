package lu.domi.sapphire.minimarket.services;


import android.content.Context;
import android.support.v4.util.Pair;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.Product;

public class CartFacade {

    private static CartFacade cartInstance = null;
    private CartService cartService;
    private CurrencyService currencyService;
    private Pair<String, BigDecimal> currencyRate;

    private CartFacade() {}

    private CartFacade(final Context context) {
        cartInstance = new CartFacade();
        cartService =  new CartService(context);
        currencyService = new CurrencyService();
        currencyRate = new Pair<>("USD", new BigDecimal(1.0d));
    }

    public void insertUpdate(Product product, int quantity) {
        if (getCartService().contains(product.getArtNo())) {
            getCartService().updateEntry(product.getArtNo(), quantity);
        } else {
            getCartService().insertEntry(product, quantity);
        }
    }

    public void removeEntry(int artNo) {
        getCartService().removeEntry(artNo);
    }

    public CartEntry getEntry(int artNo) {
        return getCartService().getCartEntries().get(artNo);
    }

    public boolean hasCartEntries() {
        return getCartService().getCartEntries().size() > 0;
    }

    public void oderConfirmation() {
        // Do oder processing stuff here...
        getCartService().cleanUpCart();
    }

    public int getQuantityOf(int artNo) {
        if (getCartService().contains(artNo)) {
            return getCartService().getCartEntry(artNo).getQuanity();
        }
        return 0;
    }

    public void setCurrency(String isoCode) {
        BigDecimal rate = getCurrencyService().getExchangeRates().getRate(isoCode);
        currencyRate = new Pair<>(isoCode, rate);
    }

    public String getCurrencyIso() {
        return currencyRate.first;
    }

    public BigDecimal getCurrencyRate() {
        return currencyRate.second;
    }

    public String getCartTotal() { // TODO refactoring..
        // Add shipping costs & taxes here...
        BigDecimal subtotal = getCartService().getSubtotal();
        // Would usually be in a calculation service
        BigDecimal total = subtotal.multiply(currencyRate.second);
        DecimalFormat formatter = new DecimalFormat("###,###,###.00 " + currencyRate.first);
        return formatter.format(total);
    }

    public static CartFacade getServiceInstance(final Context context) {
        if (cartInstance == null) {
            cartInstance = new CartFacade(context);
        }
        return cartInstance;
    }

    public CartService getCartService() {
        return cartService;
    }

    public CurrencyService getCurrencyService() {
        return currencyService;
    }
}
