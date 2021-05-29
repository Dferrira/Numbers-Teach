package com.dferreira.numbers_teach.drag_and_drop

import android.annotation.TargetApi
import android.os.Build
import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice

/**
 * Handle the draggable event of draggable events
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class DroppableListener(
    private val target: ISelectedChoice,
    private val dropAction: DropAction
) : OnDragListener {


    /**
     * Define what to do in the drag action
     *
     * @param v     the view where the view is
     * @param event the drag event itself
     * @return false event not handle
     * true event handle
     */
    override fun onDrag(v: View, event: DragEvent): Boolean {
        val draggedView = event.localState as View

        val action = event.action
        when (action) {
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_ENTERED -> if (dropAction !== DropAction.NOTHING) {
                target.notifyEnteredInDroppableArea()
            }
            DragEvent.ACTION_DRAG_EXITED -> if (dropAction !== DropAction.NOTHING) {
                target.notifyLeaveDroppableArea()
            }
            DragEvent.ACTION_DROP -> if (dropAction === DropAction.NOTHING) {
                draggedView.visibility = View.VISIBLE
            } else {
                target.notifyViewSelected(draggedView)
            }
            else -> {
            }
        }
        return true
    }
}