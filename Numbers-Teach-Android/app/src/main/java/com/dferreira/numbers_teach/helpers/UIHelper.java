package com.dferreira.numbers_teach.helpers;

import android.view.View;

/**
 * Set of methods to help to handle the view
 */
public class UIHelper {

    /**
     * Toggle the visibility of a view
     *
     * @param view view that is going to get the visibility toggle
     */
    public static void toggleVisiblityView(View view) {

        switch (view.getVisibility()) {
            case View.VISIBLE:
                view.setVisibility(View.INVISIBLE);
                break;
            case View.GONE:
            case View.INVISIBLE:
                view.setVisibility(View.VISIBLE);
                break;
            default:
                view.setVisibility(View.VISIBLE);
                break;
        }

    }
}
