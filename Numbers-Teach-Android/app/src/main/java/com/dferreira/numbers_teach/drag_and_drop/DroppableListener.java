package com.dferreira.numbers_teach.drag_and_drop;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice;
import com.dferreira.numbers_teach.helpers.ErrorString;


/**
 * Handle the draggable event of draggable events
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DroppableListener implements View.OnDragListener {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "DroppableListener";

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

    /*Action to take when a view is dropped*/
    private DropAction dropAction;

    /**
     * @param dropAction Action to take when a view is drop
     */
    public void setDropAction(DropAction dropAction) {
        this.dropAction = dropAction;
    }


    /**
     * Constructor of the droppable listener
     */
    public DroppableListener() {
        this.dropAction = DropAction.MOVE_VIEW; //Drop action by default
    }


    /**
     * Define what to do in the drag action
     *
     * @param v     the view where the view is
     * @param event the drag event itself
     * @return false event not handle
     * true event handle
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        View draggedView = (View) event.getLocalState();

        if (draggedView == null) {
            Log.d(TAG, ErrorString.SOMETHING_GOES_WRONG);
            return false;
        }


        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (dropAction != DropAction.NOTHING) {
                    target.setEnteredInDroppableArea();
                }

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if (dropAction != DropAction.NOTHING) {
                    target.setLeaveDroppableArea();
                }
                break;
            case DragEvent.ACTION_DROP:
                if (dropAction == DropAction.NOTHING) {
                    draggedView.setVisibility(View.VISIBLE);
                } else {
                    target.triggerViewSelected(draggedView);
                }
                break;
            default:
                break;
        }
        return true;
    }
}
