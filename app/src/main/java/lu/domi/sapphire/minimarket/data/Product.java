package lu.domi.sapphire.minimarket.data;

import android.graphics.drawable.Drawable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Product {

    private final int artNo;
    private final String name;
    private final QuantityUnit unit;
    private final BigDecimal price;
    private final Drawable productImg;

    public Product(int artNo, String name, QuantityUnit unit, BigDecimal price, Drawable productImg) {
        this.artNo = artNo;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.productImg = productImg;
    }

    public int getArtNo() {
        return artNo;
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
