package com.dferreira.numbers_teach.lesson

import android.app.Activity
import android.util.Log
import com.dferreira.numbers_teach.generic.gestures.IGesturesHandler

/**
 * Implements the gestures handler actions handler to the sequence fragment images
 */
class LessonGesturesHandler(private val activity: Activity) : IGesturesHandler {
    private val tag = "LessonGesturesHandler"
    override fun dragToLeft() {
        LessonService.getInstance().next()
    }

    override fun dragToRight() {
        LessonService.getInstance().previous()
    }

    override fun dragUp() {
        activity.onBackPressed()
    }

    override fun dragDown() {
        Log.d(tag, "dragDown")
    }

    override fun singleTap() {
        Log.d(tag, "singleTap")
    }

    override fun doubleTap() {
        LessonService.getInstance().setIsPlaying(!LessonService.getInstance().isPlaying)
    }
}