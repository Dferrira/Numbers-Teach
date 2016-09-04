package com.dferreira.numbers_teach.lesson;

import android.app.Activity;
import android.util.Log;

import com.dferreira.numbers_teach.generic.gestures.IGesturesHandler;

/**
 * Implements the gestures handler actions handler to the sequence fragment images
 */
public class LessonGesturesHandler implements IGesturesHandler {

    private final String TAG = "LessonGesturesHandler";
    private final Activity activity;

    public LessonGesturesHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void dragToLeft() {
        LessonService.getInstance().next();
    }

    @Override
    public void dragToRight() {
        LessonService.getInstance().previous();
    }

    @Override
    public void dragUp() {
        this.activity.onBackPressed();
    }

    @Override
    public void dragDown() {
        Log.d(TAG, "dragDown");
    }

    @Override
    public void singleTap() {
        Log.d(TAG, "singleTap");
    }

    @Override
    public void doubleTap() {
        LessonService.getInstance().setIsPlaying(!LessonService.getInstance().isPlaying());
    }
}
