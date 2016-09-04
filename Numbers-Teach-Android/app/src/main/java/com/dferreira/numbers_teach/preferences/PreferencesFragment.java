package com.dferreira.numbers_teach.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.dferreira.numbers_teach.R;

/**
 * Preferences fragment of the application
 */
public class PreferencesFragment extends PreferenceFragmentCompat {


    /**
     * Inflates the preferences specified in the preferences.xml file
     *
     * @param bundle Bundle passed in the preferences
     * @param key    Key of the preferences to create
     */
    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        setPreferencesFromResource(R.xml.preferences, key);
    }

}
