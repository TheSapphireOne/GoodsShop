package lu.domi.sapphire.minimarket.data;

import java.math.BigDecimal;

public class CartEntry {

    private final String name;
    private int quantity;
    private BigDecimal basePrice;

    public CartEntry(String name, BigDecimal basePrice, int quantity) {
        this.name = name;
        this.basePrice = basePrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quanity) {
        this.quantity += quanity;
    }
}
