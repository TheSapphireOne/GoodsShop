package lu.domi.sapphire.minimarket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import lu.domi.sapphire.minimarket.data.CartEntry;
import lu.domi.sapphire.minimarket.data.ExchangeRates;
import lu.domi.sapphire.minimarket.data.event.ExchangeRateEvent;
import lu.domi.sapphire.minimarket.services.CartFacade;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG_CHECKOUT_ACTIVITY = CheckoutActivity.class.getSimpleName();
    public static final int ORDER_COMPLETED = 2;
    private ArrayAdapter<String> spinnerAdapter;
    private CheckoutAdapter checkoutAdapter;
    private Spinner changeCurrencySpinner;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO move listeners to methods (refactoring)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        totalTextView = (TextView)findViewById(R.id.total_checkout_textView);

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

        ImageButton backButton = (ImageButton) findViewById(R.id.back_checkout_ImageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.checkout_enter, R.anim.checkout_exit);
            }
        });

        changeCurrencySpinner = (Spinner) findViewById(R.id.changeCurrency_checkout_spinner);
        final ExchangeRates exchangeRates = CartFacade.getServiceInstance(CheckoutActivity.this).getCurrencyService().getExchangeRates();
        changeCurrencySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) { // TODO check why is always checking...
                    if (exchangeRates.getCurrencies().size() > 1 && exchangeRates.isUpToDate()) {
                        changeCurrencySpinner.performClick();
                    } else {
                        CartFacade.getServiceInstance(CheckoutActivity.this).getCurrencyService().requestCurrencies(CheckoutActivity.this);
                    }
                }
                return true;
            } // TODO request Permissions for android 6.0+ (Internet)
        });
        spinnerAdapter = new ArrayAdapter<>(CheckoutActivity.this, android.R.layout.simple_spinner_dropdown_item,
                exchangeRates.getCurrencies().toArray(new String[exchangeRates.getCurrencies().size()]));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCurrencySpinner.setAdapter(spinnerAdapter); // TODO set correct currency + add it to cart

        changeCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currencyIso = spinnerAdapter.getItem(position);
                CartFacade.getServiceInstance(CheckoutActivity.this).setCurrency(currencyIso);
                updatePrices();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        SparseArray<CartEntry> cartEntries = CartFacade.getServiceInstance(CheckoutActivity.this).getCartService().getCartEntries();
        checkoutAdapter = new CheckoutAdapter(cartEntries, CheckoutActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cart_entries_checkout_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
        recyclerView.setAdapter(checkoutAdapter);

        updatePrices();
    }

    private void updatePrices() {
        String total =  CartFacade.getServiceInstance(CheckoutActivity.this).getCartTotal();
        totalTextView.setText(total);
        checkoutAdapter.notifyPricesHaveChanged();
    }

    @Subscribe
    public void onDialogResponse(ExchangeRateEvent result) {
        if (result.isSuccessful()) {
            ExchangeRates exchangeRates = CartFacade.getServiceInstance(CheckoutActivity.this).getCurrencyService().getExchangeRates();
            String[] currencyList = new String[exchangeRates.getCurrencies().size()];
            currencyList = exchangeRates.getCurrencies().toArray(currencyList);
            spinnerAdapter = new ArrayAdapter<>(CheckoutActivity.this, android.R.layout.simple_spinner_dropdown_item, currencyList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            changeCurrencySpinner.setAdapter(spinnerAdapter); // TODO test, when leaving checkout
            spinnerAdapter.notifyDataSetChanged();
            changeCurrencySpinner.performClick();
        } else {
            Toast.makeText(CheckoutActivity.this, getString(R.string.tst_service_error), Toast.LENGTH_SHORT).show();
        }
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
                finish();
                overridePendingTransition(R.anim.checkout_enter, R.anim.checkout_exit);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
