package com.dferreira.numbers_teach.drag_and_drop;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

/**
 * Listen to the touch event in a draggable view and create a shadow object
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DraggableListener implements View.OnTouchListener {

    /**
     * Creates a shadow view similar to the dragged view
     *
     * @param view        Dragged view
     * @param motionEvent motion event of the dragged view
     * @return threat the event or not
     */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}
