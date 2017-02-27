package lu.domi.sapphire.minimarket.data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CartEntry {

    private String name;
    private QuantityUnit unit;
    private int quantity;
    private BigDecimal basePrice;

    public CartEntry(String name, QuantityUnit unit, BigDecimal basePrice, int quantity) {
        this.name = name;
        this.unit = unit;
        this.basePrice = basePrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public QuantityUnit getUnit() {
        return unit;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getQuanity() {
        return quantity;
    }

    public void updateQuanity(int quanity) {
        this.quantity += quanity;
    }

    public String getTotalPrice() { // TODO priceService
        return NumberFormat.getCurrencyInstance(Locale.US).format(basePrice.multiply(new BigDecimal(quantity)));
    }
}