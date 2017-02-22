package lu.domi.sapphire.goodsshop.data;

import android.graphics.drawable.Drawable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Product {

    private String name;
    private QuantityUnit unit;
    private BigDecimal price;
    private Drawable productImg;

    public Product(String name, QuantityUnit unit, BigDecimal price, Drawable productImg) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.productImg = productImg;
    }

    public String getName() {
        return name;
    }

    public QuantityUnit getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Drawable getProductImg() {
        return productImg;
    }

    public String getFormatedPrice() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }
}
