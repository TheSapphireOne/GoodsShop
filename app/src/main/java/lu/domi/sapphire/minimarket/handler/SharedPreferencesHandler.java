package lu.domi.sapphire.minimarket.handler;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHandler {

    private static final String TAG_PREFS = SharedPreferencesHandler.class.getSimpleName();
    public static final String PREFS_NAME = "ShoppingPrefsFile";
    private SharedPreferences sharedPrefs;

    public SharedPreferencesHandler(final Context context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

//    public void saveAll(ArrayList<Product> players) {
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        for (Product pl : players) {
//            editor.putString(pl.getPk(), playerToString(pl));
//        }
//        editor.apply();
//    }
//
//    public void insert(Product pl) {
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putString(pl.getPk(), playerToString(pl));
//        editor.apply();
//    }
//
//    @SuppressWarnings("unchecked")
//    public ArrayList<Product> loadAll() {
//        ArrayList<Product> players = new ArrayList<>();
//        Map<String, String> data = (Map<String, String>)sharedPrefs.getAll();
//        Product pl;
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            if (entry.getKey().equals(KEY_SETTINGS)) {
//                settings = stringToSettings(entry.getValue());
//            } else {
//                pl = stringToGood(entry.getValue());
//                pl.setPk(entry.getKey());
//                players.add(pl);
//            }
//        }
//        return players;
//    }
//
//    /**
//     * Delete player from shared preferences
//     * @param pk
//     * @return false, if entry wasn't found
//     */
//    public boolean delete(String pk) {
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        if (sharedPrefs.contains(pk)) {
//            editor.remove(pk);
//            editor.apply();
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Update existing player in shared preferences
//     * @param player
//     * @return false, if entry was not found
//     */
//    public boolean update(Product player) {
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        if (sharedPrefs.contains(player.getPk())) {
//            editor.putString(player.getPk(), playerToString(player));
//            editor.apply();
//            return true;
//        }
//        return false;
//    }
//
//    public void updateAll(ArrayList<Product> players) {
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        for (Product pl : players) {
//            if (sharedPrefs.contains(pl.getPk())) {
//                editor.putString(pl.getPk(), playerToString(pl));
//            }
//        }
//        editor.apply();
//    }
//
////    public boolean insertUpdateSettings(Settings settings) {
////        SharedPreferences.Editor editor = sharedPrefs.edit();
////        editor.putString(KEY_SETTINGS, settingsToString(settings));
////        editor.apply();
////        return true;
////    }
////
////    public Settings getSettings() {
////        if (settings != null) {
////            return settings;
////        } else {
////            String setStr = sharedPrefs.getString(KEY_SETTINGS, "");
////            return stringToSettings(setStr);
////        }
////    }
//
//    private String goodToString(Product product) {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            jsonObj.put("name", product.getName());
//            jsonObj.put("color", product.getColor());
//            jsonObj.put("score", product.getScore());
//        } catch (JSONException e) {
//            Log.e(TAG_PREFS, "Product to JSON error: " + e);
//        }
//        return jsonObj.toString();
//    }
//
//    private Product stringToGood(String gooddString) {
//        Product product = null;
//        try {
//            JSONObject reader = new JSONObject(product);
//            String name =reader.getString("name");
//            int color = reader.getInt("color");
//            long score = reader.getLong("score");
//            product = new Product(name, color, score);
//        } catch (JSONException e) {
//            Log.e(TAG_PREFS, "JSON to Product error: " + e);
//        }
//        return product;
//    }

//    private String settingsToString(Settings set) {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            jsonObj.put("inverted", set.isInverted());
//            jsonObj.put("absolute", set.isAbsolute());
//        } catch (JSONException e) {
//            Log.e(TAG_PREFS, "Settings to JSON error: " + e);
//        }
//        return jsonObj.toString();
//    }
//
//    private Settings stringToSettings(String sStr) {
//        if (sStr.isEmpty()) {
//            return new Settings(false, false);
//        }
//        Settings set = null;
//        try {
//            JSONObject reader = new JSONObject(sStr);
//            boolean isInverted = reader.getBoolean("inverted");
//            boolean isAbsolute = reader.getBoolean("absolute");
//            set = new Settings(isInverted, isAbsolute);
//        } catch (JSONException e) {
//            Log.e(TAG_PREFS, "JSON to Settings error: " + e);
//        }
//        return set;
//    }

}