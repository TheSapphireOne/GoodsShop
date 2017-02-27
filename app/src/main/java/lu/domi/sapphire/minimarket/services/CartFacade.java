package lu.domi.sapphire.minimarket.services;


import android.content.Context;

import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.handler.SharedPreferencesHandler;

public class CartFacade {

    private SharedPreferencesHandler prefsHandler;
    private static CartFacade cartInstance = null;
    private CartService cartService;

    private CartFacade() {}

    private CartFacade(final Context context) {
        cartInstance = new CartFacade();
        cartService =  new CartService();
        prefsHandler = new SharedPreferencesHandler(context);
    }

    public void insertUpdate(Product product, int quantity) {
        if (getCartService().cartContains(product.getArtNo())) {
            getCartService().updateEntry(product.getArtNo(), quantity);
        } else {
            getCartService().insertEntry(product, quantity);
        }
    }

    public void removeAll(int artNo) {
        getCartService().removeEntry(artNo);
    }

    public boolean hasCartEntries() {
        return getCartService().getCartEntries().size() > 0;
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
}
