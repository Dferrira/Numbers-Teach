package com.dferreira.numbers_teach.generic.gestures

import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import java.lang.Exception
import java.util.*
import kotlin.math.abs

/**
 * Detects if the user have did any action that is supported by the application
 */
class SwipeDetector
/**
 * @param handler responsible to treat the gestures from the user
 */(  //Handler that is going to be called when an action is detected
    private val handler: IGesturesHandler
) : SimpleOnGestureListener() {
    private var lastTapTimestamp: Long = 0

    /**
     * Notified of a fling event when it occurs with the initial on down [MotionEvent]
     * and the matching up [MotionEvent]. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param firstDownMotionEvent        The first down motion event that started the fling.
     * @param currentMotionEvent        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     * along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     * along the y axis.
     * @return true if the event is consumed, else false
     */
    override fun onFling(
        firstDownMotionEvent: MotionEvent,
        currentMotionEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        try {
            val diffAbsY = abs(firstDownMotionEvent.y - currentMotionEvent.y)
            val diffX = firstDownMotionEvent.x - currentMotionEvent.x
            val diffAbsX = abs(firstDownMotionEvent.x - currentMotionEvent.x)
            val diffY = firstDownMotionEvent.y - currentMotionEvent.y
            if (diffAbsY < MAX_DISTANCE_Y) {
                // Left swipe
                if (diffX > MIN_DISTANCE_X
                    && abs(velocityX) > THRESHOLD_SPEED_X
                ) {
                    handler.dragToLeft()
                    return true

                    // Right swipe
                } else if (-diffX > MIN_DISTANCE_X
                    && abs(velocityX) > THRESHOLD_SPEED_X
                ) {
                    handler.dragToRight()
                    return true
                }
            }
            if (diffAbsX < MAX_DISTANCE_X) {
                // Up swipe
                if (diffY > MIN_DISTANCE_Y
                    && Math.abs(velocityY) > THRESHOLD_SPEED_Y
                ) {
                    handler.dragUp()
                    return true

                    // Down swipe
                } else if (-diffY > MIN_DISTANCE_Y
                    && Math.abs(velocityY) > THRESHOLD_SPEED_Y
                ) {
                    handler.dragDown()
                    return true
                }
            }
        } catch (ignored: Exception) {
        }
        return false
    }

    /**
     * Notified when a tap occurs with the up [MotionEvent]
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        handler.singleTap()
        return super.onSingleTapConfirmed(e)
    }

    /**
     * Notified when an event within a double-tap gesture occurs, including
     * the down, move, and up events.
     *
     * @param e The motion event that occurred during the double-tap gesture.
     * @return true if the event is consumed, else false
     */
    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        val currentDate = Date()
        val currentDetected = currentDate.time
        if (currentDetected - lastTapTimestamp > THRESHOLD_LAST_DOUBLE_TAP) {
            lastTapTimestamp = currentDetected
            handler.doubleTap()
        }
        return super.onDoubleTap(e)
    }

    companion object {
        private const val MIN_DISTANCE_X = 120
        private const val MAX_DISTANCE_X = 200
        private const val THRESHOLD_SPEED_X = 200
        private const val MAX_DISTANCE_Y = 200
        private const val MIN_DISTANCE_Y = 120
        private const val THRESHOLD_SPEED_Y = 200
        private const val THRESHOLD_LAST_DOUBLE_TAP = 1000
    }
}