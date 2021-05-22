package com.dferreira.numbers_teach.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.dferreira.numbers_teach.R

/**
 * Preferences fragment of the application
 */
class PreferencesFragment : PreferenceFragmentCompat() {
    /**
     * Inflates the preferences specified in the preferences.xml file
     *
     * @param bundle Bundle passed in the preferences
     * @param key    Key of the preferences to create
     */
    override fun onCreatePreferences(bundle: Bundle?, key: String?) {
        setPreferencesFromResource(R.xml.preferences, key)
    }
}