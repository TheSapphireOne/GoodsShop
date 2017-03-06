package lu.domi.sapphire.minimarket.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

import lu.domi.sapphire.minimarket.data.CartEntry;

public class SharedPreferencesHandler {

    private static final String TAG_PREFS = SharedPreferencesHandler.class.getSimpleName();
    private static final String PREFS_NAME = "MiniMarketPrefsFile";
    private final SharedPreferences sharedPrefs;

    public SharedPreferencesHandler(final Context context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void insertUpdate(CartEntry entry, int artNo) {
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        editor.putString("" + artNo, convertCartEntry(entry));
        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public SparseArray<CartEntry> loadAll() {
        SparseArray<CartEntry> cartEntries = new SparseArray<>();
        CartEntry entry;

        Map<String, String> data = (Map<String, String>) getSharedPrefs().getAll();

        for (Map.Entry<String, String> dataEntry : data.entrySet()) {
            entry = populateCartEntry(dataEntry.getValue());
            cartEntries.put(Integer.parseInt(dataEntry.getKey()), entry);
        }
        return cartEntries;
    }

    public void delete(int artNo) {
        String key = "" + artNo;
        SharedPreferences.Editor editor = getSharedPrefs().edit();
        if (getSharedPrefs().contains(key)) {
            editor.remove(key);
            editor.apply();
        }
    }

    public void deleteAll() {
        getSharedPrefs().edit().clear().apply();
    }

    // Usually we would use populators/converts for this case
    private String convertCartEntry(CartEntry entry) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("name", entry.getName());
            jsonObj.put("quantity", entry.getQuantity());
            jsonObj.put("price", entry.getBasePrice().doubleValue());
        } catch (JSONException e) {
            Log.e(TAG_PREFS, "CartEntry to JSON error: " + e);
        }
        return jsonObj.toString();
    }

    private CartEntry populateCartEntry(String cartEntryStr) {
        CartEntry entry = null;
        try {
            JSONObject reader = new JSONObject(cartEntryStr);
            String name =reader.getString("name");
            int quantity = reader.getInt("quantity");
            BigDecimal price = new BigDecimal(reader.getDouble("price"));
            entry = new CartEntry(name, price, quantity);
        } catch (JSONException e) {
            Log.e(TAG_PREFS, "JSON to CartEntry error: " + e);
        }
        return entry;
    }

    private SharedPreferences getSharedPrefs() {
        return sharedPrefs;
    }
}