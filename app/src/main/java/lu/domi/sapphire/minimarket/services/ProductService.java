package lu.domi.sapphire.minimarket.services;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.math.BigDecimal;
import java.util.ArrayList;

import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.Product;

import static lu.domi.sapphire.minimarket.data.QuantityUnit.*;

public class ProductService {

    private static ProductService productServiceInstance = null;
    private ArrayList<Product> products = new ArrayList<>();

    private ProductService() {}

    public ArrayList<Product> getProducts(Context context) {
        if (products.isEmpty()) {
            initSampleProducts(context);
        }
        return products;
    }

    private void initSampleProducts(Context context) {
        products.add(new Product(111, context.getResources().getString(R.string.pr_tomatoes),
                BAG, new BigDecimal(0.95), ContextCompat.getDrawable(context, R.drawable.tomatoes)));
        products.add(new Product(222, context.getResources().getString(R.string.pr_eggs),
                DOZEN, new BigDecimal(2.10), ContextCompat.getDrawable(context, R.drawable.eggs)));
        products.add(new Product(333, context.getResources().getString(R.string.pr_milk),
                BOTTLE, new BigDecimal(1.30), ContextCompat.getDrawable(context, R.drawable.milk)));
        products.add(new Product(444, context.getResources().getString(R.string.pr_beans),
                CAN, new BigDecimal(0.73), ContextCompat.getDrawable(context, R.drawable.beans)));
    }

    public static ProductService getProductServiceInstance() {
        if (productServiceInstance == null) {
            productServiceInstance = new ProductService();
        }
        return productServiceInstance;
    }
}
