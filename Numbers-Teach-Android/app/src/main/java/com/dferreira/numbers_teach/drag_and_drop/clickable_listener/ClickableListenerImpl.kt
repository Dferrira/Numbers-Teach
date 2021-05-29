package com.dferreira.numbers_teach.drag_and_drop.clickable_listener

import android.view.View
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice

/**
 * Handle the on click of image previous to honeycomb API release
 */
class ClickableListenerImpl(
    private val target: ISelectedChoice
) : View.OnClickListener {

    /**
     * Called when a view has been clicked.
     *
     * @param selectedView The view that was clicked.
     */
    override fun onClick(selectedView: View) {
        target.notifyViewSelected(selectedView)
        selectedView.visibility = View.GONE
    }
}