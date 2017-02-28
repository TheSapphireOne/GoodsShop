package lu.domi.sapphire.minimarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.FragmentForwardingResult;
import lu.domi.sapphire.minimarket.fragments.CartDialogFragment;
import lu.domi.sapphire.minimarket.services.CartFacade;
import lu.domi.sapphire.minimarket.services.ProductService;

import static lu.domi.sapphire.minimarket.data.FragmentForwardingResult.CHECKOUT;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG_CHECKOUT_ACTIVITY = CheckoutActivity.class.getSimpleName();
    private CheckoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.checkout_toolbar);
        toolbar.setTitle(getString(R.string.title_checkout));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



//        ImageButton cartBtn = (ImageButton) toolbar.findViewById(R.id.cart_toolbar_imgBtn);
//        cartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCart();
//            }
//        });

        SparseArray<CartEntry> cartEntries = CartFacade.getServiceInstance(CheckoutActivity.this).getCartService().getCartEntries();
        adapter = new CheckoutAdapter(cartEntries, CheckoutActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cart_entries_checkout_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
