package lu.domi.sapphire.minimarket.service;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.data.QuantityUnit;
import lu.domi.sapphire.minimarket.services.CartFacade;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CartFacadeTest {

    private CartFacade cartFacade = CartFacade.getServiceInstance(InstrumentationRegistry.getContext());

    // Test products
    private Product testProduct1 = new Product(11, "prod1", QuantityUnit.BAG, new BigDecimal(12.1d), null);
    private Product testProduct2 = new Product(22, "prod2", QuantityUnit.BOTTLE, new BigDecimal(75.0d), null);
    private Product testProduct3 = new Product(33, "prod3", QuantityUnit.CAN, new BigDecimal(36.35d), null);

    @Test
    public void insertProduct() {
        cartFacade.insertUpdate(testProduct1, 1);
        cartFacade.insertUpdate(testProduct2, 100);

        assertEquals("Product 1 name", testProduct1.getName(),
                cartFacade.getEntry(testProduct1.getArtNo()).getName());
        assertEquals("Product 1 price", testProduct1.getPrice(),
                cartFacade.getEntry(testProduct1.getArtNo()).getBasePrice());
        assertEquals("Items in cart", 2, cartFacade.getCartService().getCartEntries().size());
    }

    @Test
    public void removeProduct() {
        cartFacade.insertUpdate(testProduct1, 1);
        cartFacade.insertUpdate(testProduct2, 5);

        cartFacade.insertUpdate(testProduct1, -1);
        assertEquals("Product 1 removeEntry (update)", null, cartFacade.getEntry(testProduct1.getArtNo()));
        cartFacade.removeEntry(testProduct2.getArtNo());
        assertEquals("Product 2 removeEntry", null, cartFacade.getEntry(testProduct1.getArtNo()));
    }

    @Test
    public void hasCartEntries() {
        assertFalse("Empty cart", cartFacade.hasCartEntries());
        cartFacade.insertUpdate(testProduct1, 1);
        assertTrue("Product in cart", cartFacade.hasCartEntries());
        cartFacade.removeEntry(testProduct1.getArtNo());
        assertFalse("Empty cart (product removed)", cartFacade.hasCartEntries());
    }

    @Test
    public void changeCurrency() {
        assertEquals("Default currency ISO", "USD", cartFacade.getCurrencyIso());
        assertEquals("Default currency rate", new BigDecimal(1.0d), cartFacade.getCurrencyRate());

        cartFacade.getCurrencyService().getExchangeRates().addRate("CHF", new BigDecimal(1.007104d));
        cartFacade.getCurrencyService().getExchangeRates().addRate("ETB", new BigDecimal(22.503874d));
        cartFacade.getCurrencyService().getExchangeRates().addRate("EUR", new BigDecimal(0.941304d));
        cartFacade.setCurrency("EUR");
        assertEquals("New currency ISO", "EUR", cartFacade.getCurrencyIso());
        assertEquals("New currency rate", new BigDecimal(0.941304d), cartFacade.getCurrencyRate());
    }

    @Test
    public void caluclateCartTotal() {
        cartFacade.insertUpdate(testProduct1, 1);
        cartFacade.insertUpdate(testProduct2, 2);
        cartFacade.insertUpdate(testProduct3, 5);

        assertEquals("Subtotal", "343.85 USD", cartFacade.getCartTotal());

        cartFacade.getCurrencyService().getExchangeRates().addRate("GBP",new BigDecimal(0.81335d));
        cartFacade.setCurrency("GBP");
        assertEquals("Subtotal", "279.67 GBP", cartFacade.getCartTotal());

        cartFacade.getCurrencyService().getExchangeRates().addRate("COP", new BigDecimal(2968.800049d));
        cartFacade.setCurrency("COP");
        assertEquals("Subtotal", "1,020,821.90 COP", cartFacade.getCartTotal());
    }

    @Test
    public void updateProductQunatity() {
        cartFacade.insertUpdate(testProduct1, 1);
        cartFacade.insertUpdate(testProduct1, 3);

        assertEquals("Product qunatity (added)", 4, cartFacade.getQuantityOf(testProduct1.getArtNo()));
        cartFacade.insertUpdate(testProduct1, -2);
        assertEquals("Product qunatity (removed)", 2, cartFacade.getQuantityOf(testProduct1.getArtNo()));
    }

    @After
    public void after() {
        cartFacade.getCartService().cleanUpCart();
        cartFacade.setCurrency("USD");
    }
}
