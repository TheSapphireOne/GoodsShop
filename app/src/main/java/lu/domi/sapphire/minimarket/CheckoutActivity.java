package lu.domi.sapphire.minimarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.services.CartFacade;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG_CHECKOUT_ACTIVITY = CheckoutActivity.class.getSimpleName();
    public static final int ORDER_COMPLETED = 2;
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

        Button confirmBtn = (Button) findViewById(R.id.checkout_cart_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process order here...
                CartFacade.getServiceInstance(CheckoutActivity.this).oderConfirmation();
                CheckoutActivity.this.setResult(ORDER_COMPLETED);
                finish();
            }
        });

        SparseArray<CartEntry> cartEntries = CartFacade.getServiceInstance(CheckoutActivity.this).getCartService().getCartEntries();
        adapter = new CheckoutAdapter(cartEntries, CheckoutActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cart_entries_checkout_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.checkout_enter, R.anim.checkout_exit);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // TODO check if needed
                overridePendingTransition(R.anim.checkout_enter, R.anim.checkout_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
