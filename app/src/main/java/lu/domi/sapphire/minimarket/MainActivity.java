package lu.domi.sapphire.minimarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import lu.domi.sapphire.minimarket.data.FragmentForwardingResult;
import lu.domi.sapphire.minimarket.fragments.CartDialogFragment;
import lu.domi.sapphire.minimarket.services.CartFacade;
import lu.domi.sapphire.minimarket.services.ProductService;

import static lu.domi.sapphire.minimarket.data.FragmentForwardingResult.CHECKOUT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    private static final int CHECKOUT_ACTIVITY = 10;
    private static final String CART = "cart";
    private Button checkoutBtn;
    private ShopAdapter adapter;

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
                showCart();
            }
        });

        checkoutBtn = (Button) findViewById(R.id.checkout_cart_button);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartFacade.getServiceInstance(MainActivity.this).hasCartEntries()) {
                    showCheckout(); // TODO show Button only when got something in cart
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.tst_cart_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter = new ShopAdapter(ProductService.getProductServiceInstance().getProducts(MainActivity.this),
                MainActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.products_main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void showCart() {
        if (CartFacade.getServiceInstance(MainActivity.this).hasCartEntries()) {
            CartDialogFragment addEditGroupDialog = new CartDialogFragment();
            addEditGroupDialog.show(getSupportFragmentManager(), MainActivity.CART);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.tst_cart_empty), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onDialogResponse(FragmentForwardingResult result) {
        if (result == CHECKOUT) {
            showCheckout();
        }
    }

    private void showCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivityForResult(intent, CHECKOUT_ACTIVITY);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
