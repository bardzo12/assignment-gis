package sk.pixwell.therun.data.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class SharedPreferencesUtils {


    /**
     * Function for getting sharedPreferences. You can set mode or name of preferences here
     * @param context - application context
     * @return SharedPreferences
     */
    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     *
     * @param context
     * @param name - settings name
     * @param def - default value to return
     * @return - returns value from shared preferences
     */
    public static int getIntFromPreferences(Context context, String name, int def){
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getInt(name,def);
    }

    /**
     *
     * @param context
     * @param name - settings name
     * @param def - default value to return
     * @return - returns value from shared preferences
     */
    public static String getStringFromPreferences(Context context, String name, String def){
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getString(name,def);
    }

    /**
     *
     * @param context
     * @param name - settings name
     * @param def - default value to return
     * @return - returns value from shared preferences
     */
    public static boolean getBooleanFromPreferences(Context context, String name, boolean def){
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(name,def);
    }

    /**
     *
     * @param context
     * @param name - settings name
     * @param def - default value to return
     * @return - returns value from shared preferences
     */
    public static float getFloatFromPreferences(Context context, String name, float def){
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getFloat(name,def);
    }


    /**
     *
     * @param context
     * @param name - settings name
     * @param def - default value to return
     * @return - returns value from shared preferences
     */
    public static float getLongFromPreferences(Context context, String name, long def){
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getLong(name,def);
    }

    /**
     * Function for saving to {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference under which, you will later access it
     * @param value to be saved
     */
    public static void savePreference(Context context, String name, boolean value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    /**
     * Function for saving to {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference under which, you will later access it
     * @param value to be saved
     */
    public static void savePreference(Context context, String name, int value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    /**
     * Function for saving to {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference under which, you will later access it
     * @param value to be saved
     */
    public static void savePreference(Context context, String name, long value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(name, value);
        editor.apply();
    }

    /**
     * Function for saving to {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference under which, you will later access it
     * @param value to be saved
     */
    public static void savePreference(Context context, String name, float value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    /**
     * Function for saving to {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference under which, you will later access it
     * @param value to be saved
     */
    public static void savePreference(Context context, String name, String value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    /**
     * Function for removing from {@link SharedPreferences}
     * @param context self-sufficient
     * @param name of preference to delete
     */
    public static void removePreference(Context context, String name) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(name);
        editor.apply();
    }

    private static void removeAppPreferences(Context context){
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
