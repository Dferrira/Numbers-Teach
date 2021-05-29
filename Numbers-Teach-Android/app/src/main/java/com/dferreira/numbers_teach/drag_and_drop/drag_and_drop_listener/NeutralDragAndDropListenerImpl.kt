package com.dferreira.numbers_teach.drag_and_drop.drag_and_drop_listener

import android.view.DragEvent
import android.view.View

class NeutralDragAndDropListenerImpl : View.OnDragListener {
    /**
     * Define what to do in the drag action
     *
     * @param view     the view where the view is
     * @param event the drag event itself
     * @return false event not handle
     * true event handle
     */
    override fun onDrag(view: View, event: DragEvent): Boolean {

        val action = event.action
        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DROP -> {
                val draggedView = event.localState as View
                draggedView.visibility = View.VISIBLE
            }
        }
        return true
    }
}