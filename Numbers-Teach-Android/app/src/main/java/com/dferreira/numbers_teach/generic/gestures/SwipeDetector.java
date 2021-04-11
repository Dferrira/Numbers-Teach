package com.dferreira.numbers_teach.generic.gestures;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import java.util.Date;

/**
 * Detects if the user have did any action that is supported by the application
 */
public class SwipeDetector extends SimpleOnGestureListener {
    private static final int MIN_DISTANCE_X = 120;
    private static final int MAX_DISTANCE_X = 200;
    private static final int THRESHOLD_SPEED_X = 200;
    private static final int MAX_DISTANCE_Y = 200;
    private static final int MIN_DISTANCE_Y = 120;
    private static final int THRESHOLD_SPEED_Y = 200;


    private static final int THRESHOLD_LAST_DOUBLE_TAP = 1000;
    //Handler that is going to be called when an action is detected
    private final IGesturesHandler handler;
    private long lastTapTimestamp;

    /**
     * @param handler responsible to treat the gestures from the user
     */
    public SwipeDetector(IGesturesHandler handler) {
        this.handler = handler;
        lastTapTimestamp = 0;
    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        try {
            float diffAbsY = Math.abs(e1.getY() - e2.getY());
            float diffX = e1.getX() - e2.getX();

            float diffAbsX = Math.abs(e1.getX() - e2.getX());
            float diffY = e1.getY() - e2.getY();

            if (diffAbsY < MAX_DISTANCE_Y) {
                // Left swipe
                if (diffX > MIN_DISTANCE_X
                        && Math.abs(velocityX) > THRESHOLD_SPEED_X) {

                    handler.dragToLeft();
                    return true;

                    // Right swipe
                } else if (-diffX > MIN_DISTANCE_X
                        && Math.abs(velocityX) > THRESHOLD_SPEED_X) {
                    handler.dragToRight();
                    return true;
                }
            }
            if (diffAbsX < MAX_DISTANCE_X) {
                // Up swipe
                if (diffY > MIN_DISTANCE_Y
                        && Math.abs(velocityY) > THRESHOLD_SPEED_Y) {
                    handler.dragUp();
                    return true;

                    // Down swipe
                } else if (-diffY > MIN_DISTANCE_Y
                        && Math.abs(velocityY) > THRESHOLD_SPEED_Y) {
                    handler.dragDown();
                    return true;
                }
            }

        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    public boolean onSingleTapUp(MotionEvent e) {
        handler.singleTap();
        return super.onSingleTapConfirmed(e);
    }

    /**
     * Notified when an event within a double-tap gesture occurs, including
     * the down, move, and up events.
     *
     * @param e The motion event that occurred during the double-tap gesture.
     * @return true if the event is consumed, else false
     */
    public boolean onDoubleTapEvent(MotionEvent e) {
        Date currentDate = new Date();
        long currentDetected = currentDate.getTime();
        if ((currentDetected - lastTapTimestamp) > THRESHOLD_LAST_DOUBLE_TAP) {
            lastTapTimestamp = currentDetected;
            handler.doubleTap();
        }
        return super.onDoubleTap(e);
    }

}
