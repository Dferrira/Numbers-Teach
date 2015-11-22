package com.dferreira.numbers_teach.sequence;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dferreira.commons.LoadUtils;
import com.dferreira.numbers_teach.R;

/**
 * Show a sequence of 3D Models representing the the numbers to teach
 */
public class SequenceActivity extends FragmentActivity {

    private final static String TAG = "SequenceActivity";
    public final static String COUNTRY_KEY = "country";

    /**
     * Tell the surface view we want to create an OpenGL ES 2.0-compatible
     * context, and set an OpenGL ES 2.0-compatible renderer.
     *
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoadUtils.detectOpenGLES20(this)) {
            this.setContentView(R.layout.sequence_activity);
        } else {
            Log.e(TAG, "OpenGL ES 2.0 not supported on device.  Exiting...");
            finish();

        }
    }
}
