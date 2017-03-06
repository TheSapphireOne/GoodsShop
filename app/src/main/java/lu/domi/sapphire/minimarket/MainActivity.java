package lu.domi.sapphire.minimarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import lu.domi.sapphire.minimarket.data.event.CartEntryEvent;
import lu.domi.sapphire.minimarket.data.event.FragmentForwardingResult;
import lu.domi.sapphire.minimarket.fragments.CartDialogFragment;
import lu.domi.sapphire.minimarket.services.CartFacade;
import lu.domi.sapphire.minimarket.services.ProductService;

import static lu.domi.sapphire.minimarket.CheckoutActivity.ORDER_COMPLETED;
import static lu.domi.sapphire.minimarket.data.event.FragmentForwardingResult.CART_ENTRY_REMOVED;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    private static final int CHECKOUT_ACTIVITY = 10;
    private static final String CART = "cart";
    private CartDialogFragment cartDialog;

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

        Button checkoutBtn = (Button) findViewById(R.id.checkout_cart_button);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartFacade.getServiceInstance(MainActivity.this).hasCartEntries()) {
                    showCheckout(); // TODO show Button only when there is something in cart (anim) + add price?
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.tst_cart_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ShopAdapter adapter = new ShopAdapter(ProductService.getProductServiceInstance().getProducts(MainActivity.this),
                MainActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.products_main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void showCart() {
        if (CartFacade.getServiceInstance(MainActivity.this).hasCartEntries()) {
            cartDialog = new CartDialogFragment();
            cartDialog.show(getSupportFragmentManager(), MainActivity.CART);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.tst_cart_empty), Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onDialogResponse(FragmentForwardingResult result) {
        switch (result) {
            case CHECKOUT:
                showCheckout();
                break;
            default:
                Log.d(TAG_MAIN_ACTIVITY, "Unknown event result, missing implementation. " + result.name());
        }
    }

    @Subscribe
    public void onDialogResponse(CartEntryEvent result) {
        CartFacade.getServiceInstance(MainActivity.this).removeEntry(result.getEntryArtNo());
        String toastMsg;
        if (result.getKey() == CART_ENTRY_REMOVED) {
            toastMsg = getString(R.string.tst_cart_entry_removed);
            cartDialog.updateSubtotal();
        } else {
            toastMsg = getString(R.string.tst_cart_empty);
            cartDialog.dismiss();
        }
        Toast.makeText(MainActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
    }

    private void showCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivityForResult(intent, CHECKOUT_ACTIVITY);
        overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ORDER_COMPLETED) {
            Toast confirmationToast = Toast.makeText(MainActivity.this, getString(R.string.tst_order_completed), Toast.LENGTH_LONG);
            confirmationToast.setGravity(Gravity.CENTER, 0, 0);
            confirmationToast.show();
        }
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
