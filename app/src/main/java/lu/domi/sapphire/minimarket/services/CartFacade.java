package lu.domi.sapphire.minimarket.services;


import android.content.Context;

import lu.domi.sapphire.minimarket.data.Product;

public class CartFacade {

    private static CartFacade cartInstance = null;
    private CartService cartService;

    private CartFacade() {}

    private CartFacade(final Context context) {
        cartInstance = new CartFacade();
        cartService =  new CartService(context);
    }

    public void insertUpdate(Product product, int quantity) {
        if (getCartService().contains(product.getArtNo())) {
            getCartService().updateEntry(product.getArtNo(), quantity);
        } else {
            getCartService().insertEntry(product, quantity);
        }
    }

    public void remove(int artNo) {
        getCartService().removeEntry(artNo);
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
