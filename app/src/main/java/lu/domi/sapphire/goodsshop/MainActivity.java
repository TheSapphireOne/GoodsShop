package lu.domi.sapphire.goodsshop;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

import lu.domi.sapphire.goodsshop.data.Product;
import lu.domi.sapphire.goodsshop.handler.SharedPreferencesHandler;

import static lu.domi.sapphire.goodsshop.data.QuantityUnit.BAG;
import static lu.domi.sapphire.goodsshop.data.QuantityUnit.BOTTLE;
import static lu.domi.sapphire.goodsshop.data.QuantityUnit.CAN;
import static lu.domi.sapphire.goodsshop.data.QuantityUnit.DOZEN;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    private ShopAdapter adapter;
    private SharedPreferencesHandler prefsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefsHandler = new SharedPreferencesHandler(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);

        adapter = new ShopAdapter(getProducts(), getResources());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.goods_main_recyclerView);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.reset_scores:
//                if (adapter.hasGoods()) {
//                    showConfirmationDialogReset();
//                }
//                return true;
//            case R.id.menu_setting_absolute:
//                toggleSettingsAbsolute();
//                return true;
//            case R.id.menu_setting_invert:
//                toggleSettingsInvert();
//                return true;
//            case R.id.delete_all_players:
//                if (adapter.hasGoods()) {
//                    showConfirmationDialogDelete();
//                } else {
//                    Toast.makeText(this, R.string.tst_no_players_del, Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//        absoluteMenuItem = menu.findItem(R.id.menu_setting_absolute);
//        InvertedMenuItem = menu.findItem(R.id.menu_setting_invert);
//        absoluteMenuItem.setChecked(settings.isAbsolute());
//        InvertedMenuItem.setChecked(settings.isInverted());
//        return true;
//    }
}
