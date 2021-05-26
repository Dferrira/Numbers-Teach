package com.dferreira.numbers_teach.languages_dashboard.views

import android.content.Context

/**
 * Helper to be used in order to get some additional
 * functionality related with dashboard
 */
object LanguageDashboardHelper {
    /**
     * @param context        Context where is to used the method
     * @param languagePrefix Prefix of the language to get the flag
     * @return The identifier to use the flag of the language passed
     */
    fun getLanguageFlagResId(
        context: Context,
        languagePrefix: String
    ): Int {
        val resourcePrefix = "${languagePrefix}_flag"
        return context.resources.getIdentifier(resourcePrefix, "drawable", context.packageName)
    }
}