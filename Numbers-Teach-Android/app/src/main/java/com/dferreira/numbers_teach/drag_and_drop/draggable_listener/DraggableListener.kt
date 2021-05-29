package com.dferreira.numbers_teach.drag_and_drop.draggable_listener

import android.annotation.TargetApi
import android.content.ClipData
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener

/**
 * Listen to the touch event in a draggable view and create a shadow object
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class DraggableListener : OnTouchListener {
    /**
     * Creates a shadow view similar to the dragged view
     *
     * @param view        Dragged view
     * @param motionEvent motion event of the dragged view
     * @return threat the event or not
     */
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return if (motionEvent.action == MotionEvent.ACTION_DOWN) {
            onTouchActionDown(view)
        } else {
            false
        }
    }

    private fun onTouchActionDown(view: View): Boolean {
        val data = ClipData.newPlainText("", "")
        val shadowBuilder = DragShadowBuilder(view)
        view.startDrag(data, shadowBuilder, view, 0)
        view.visibility = View.INVISIBLE
        return true
    }
}