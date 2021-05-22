package com.dferreira.numbers_teach.preferences

import android.content.Context
import android.preference.PreferenceManager

/**
 * Utils to provide the values of the preferences selected
 */
object PreferencesUtils {
    /**
     * @param context Get the delay betweenSlides
     * @return Delay in milliseconds between slides
     */
    fun getDelayBetweenSlides(context: Context): Int {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val value = preferences.getString(PreferencesKeys.SEQUENCE_DELAY_KEY, "")
        return if (value.isNullOrEmpty()) {
            0
        } else {
            value.toInt()
        }
    }
}