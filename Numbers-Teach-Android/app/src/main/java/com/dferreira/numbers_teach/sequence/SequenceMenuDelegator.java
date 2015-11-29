package com.dferreira.numbers_teach.sequence;

import android.util.Log;

/**
 * Handles the press of the buttons of the menu
 */
public class SequenceMenuDelegator {

private final String TAG = "SequenceMenuDelegator";

    public void reload() {
        Log.d(TAG, "reload");
    }

    public void previous() {
        Log.d(TAG, "previous");

    }

    public void playAndPause() {
        Log.d(TAG, "playAndPause");

    }

    public void forward() {
        Log.d(TAG, "forward");

    }
}
