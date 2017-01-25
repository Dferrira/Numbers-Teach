package com.dferreira.numbers_teach.languages_dashboard.views;

import android.content.Context;

/**
 * Helper to be used in order to get some additional
 * functionality related with dashboard
 */
public class LanguageDashboardHelper {

    /**
     * @param context        Context where is to used the method
     * @param languagePrefix Prefix of the language to get the flag
     * @return The identifier to use the flag of the language passed
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static Integer getLanguageFlagResId(Context context, String languagePrefix) {
        String resourcePrefix = languagePrefix + "_flag";

        int resourceId = context.getResources().getIdentifier(resourcePrefix, "drawable", context.getPackageName());
        return resourceId;
    }
}
