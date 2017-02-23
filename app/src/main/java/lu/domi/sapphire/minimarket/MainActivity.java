package lu.domi.sapphire.minimarket;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.math.BigDecimal;
import java.util.ArrayList;

import lu.domi.sapphire.minimarket.data.Product;
import lu.domi.sapphire.minimarket.handler.SharedPreferencesHandler;

import static lu.domi.sapphire.minimarket.data.QuantityUnit.BAG;
import static lu.domi.sapphire.minimarket.data.QuantityUnit.BOTTLE;
import static lu.domi.sapphire.minimarket.data.QuantityUnit.CAN;
import static lu.domi.sapphire.minimarket.data.QuantityUnit.DOZEN;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    private ShopAdapter adapter;
    private SharedPreferencesHandler prefsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ImageButton cartBtn = (ImageButton) toolbar.findViewById(R.id.cart_toolbar_imgBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO impl cart view
            }
        });

        prefsHandler = new SharedPreferencesHandler(this);


        adapter = new ShopAdapter(getProducts(), getResources());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.goods_main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(getResources()
                .getString(R.string.pr_tomatoes), BAG, new BigDecimal(0.95), ContextCompat.getDrawable(MainActivity.this, R.drawable.tomatoes)));
        products.add(new Product(getResources()
                .getString(R.string.pr_eggs), DOZEN, new BigDecimal(2.10), ContextCompat.getDrawable(MainActivity.this, R.drawable.eggs)));
        products.add(new Product(getResources()
                .getString(R.string.pr_milk), BOTTLE, new BigDecimal(1.30), ContextCompat.getDrawable(MainActivity.this, R.drawable.milk)));
        products.add(new Product(getResources()
                .getString(R.string.pr_beans), CAN, new BigDecimal(0.73), ContextCompat.getDrawable(MainActivity.this, R.drawable.beans)));
        return  products;
    }
}
