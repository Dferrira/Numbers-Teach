package com.dferreira.numbers_teach.drag_and_drop;

import android.util.Log;
import android.view.View;

import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice;

/**
 * Handle the on click of image previous to honeycomb API release
 */
public class ClickableListener implements View.OnClickListener {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "ClickableListener";

    /*Specify where content will be set*/
    private ISelectedChoice target;

    /**
     * Specify where content will be set
     *
     * @param target view that is going to get the dropped view
     */
    public void setTarget(ISelectedChoice target) {
        this.target = target;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param selectedView The view that was clicked.
     */
    @Override
    public void onClick(View selectedView) {
        if (this.target == null) {
            Log.d(TAG, "Something goes wrong");
        } else {
            target.triggerViewSelected(selectedView);
            selectedView.setVisibility(View.GONE);
        }
    }
}
