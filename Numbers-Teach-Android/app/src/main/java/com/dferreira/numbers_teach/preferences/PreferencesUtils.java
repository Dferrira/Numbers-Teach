package com.dferreira.numbers_teach.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Utils to provide the values of the preferences selected
 */
public class PreferencesUtils {
    /**
     * @param context Get the delay betweenSlides
     * @return Delay in milliseconds between slides
     */
    public static int getDelayBetweenSlides(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String value = prefs.getString(PreferencesKeys.SEQUENCE_DELAY_KEY, "");
        if (TextUtils.isEmpty(value)) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }
}
