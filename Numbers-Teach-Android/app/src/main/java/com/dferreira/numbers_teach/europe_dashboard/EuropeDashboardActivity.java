package com.dferreira.numbers_teach.europe_dashboard;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dferreira.commons.LoadUtils;
import com.dferreira.numbers_teach.R;

/**
 * Show An activity with the map of Europe with countries to select
 */
public class EuropeDashboardActivity extends FragmentActivity {

    private final static String TAG = "EuropeDashboardActivity";

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
            this.setContentView(R.layout.europe_dashboard_activity);
        } else {
            Log.e(TAG, "OpenGL ES 2.0 not supported on device.  Exiting...");
            finish();

        }
    }
}
