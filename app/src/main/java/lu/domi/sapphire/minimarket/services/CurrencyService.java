package lu.domi.sapphire.minimarket.services;

import android.app.ProgressDialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Iterator;

import lu.domi.sapphire.minimarket.AppController;
import lu.domi.sapphire.minimarket.R;
import lu.domi.sapphire.minimarket.data.ExchangeRates;
import lu.domi.sapphire.minimarket.data.event.ExchangeRateEvent;

public class CurrencyService {

    private static final String TAG_CURRENCY_SERVICE = CurrencyService.class.getSimpleName();
    private static final String ACCESS_KEY_VAR = "?access_key=fbe34fc249d89609965ed6958c9bea35";
    private static final String BASE_URL = "http://apilayer.net/api/";
    private static final String ENDPOINT = "live";
    private static final String TAG_JSON_CURRENCY_ALL = "tag_json_currency_all";
    private ExchangeRates exchangeRates;

    public void requestCurrencies(Context context) {
        final ProgressDialog pSpinner = new ProgressDialog(context);
        pSpinner.setMessage(context.getString(R.string.checkout_currency_request));
        pSpinner.show();

        String url = BASE_URL + ENDPOINT + ACCESS_KEY_VAR;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String jsonString = response.toString();
                        Log.d(TAG_CURRENCY_SERVICE, jsonString);
                        ExchangeRates exchangeRates = convertToExchangeRates(jsonString);
                        EventBus.getDefault().post(new ExchangeRateEvent(exchangeRates, true));
                        pSpinner.hide();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG_CURRENCY_SERVICE, "Error: " + error.getMessage());
                        EventBus.getDefault().post(new ExchangeRateEvent(false));
                        pSpinner.hide();
                    }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG_JSON_CURRENCY_ALL);
    }

    private ExchangeRates convertToExchangeRates(String input) {
        exchangeRates = new ExchangeRates();
        try {
            JSONObject reader = new JSONObject(input);
            // TODO check source = USD -> otherwise error...
            exchangeRates.setTimestamp(System.currentTimeMillis());
            JSONObject dataObj = new JSONObject(reader.getString("quotes").trim());

            Iterator<?> keys = dataObj.keys();

            while(keys.hasNext()) {
                String key = (String)keys.next();
                if (key.startsWith("USD")) {
                    String currency = key.replaceFirst("USD", "");
                    BigDecimal rate = new BigDecimal(dataObj.getDouble(key));
                    exchangeRates.addRate(currency, rate);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG_CURRENCY_SERVICE, "JSON to ExchangeRates error: " + e);
        }
        return exchangeRates;
    }

    public ExchangeRates getExchangeRates() {
        if (exchangeRates == null) {
            exchangeRates = new ExchangeRates(System.currentTimeMillis(), "USD", new BigDecimal(1.0d));
        }
        return exchangeRates;
    }
}
