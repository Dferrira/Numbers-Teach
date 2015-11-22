package com.dferreira.numbers_teach.europe_dashboard;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.dferreira.numbers_teach.europe_dashboard.CountryPickerRenderer;

/**
 * View with map of Europe to choose one country
 */
public class CountryPickerView extends GLSurfaceView {

    private final CountryPickerRenderer objPickerRenderer;

    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context context where this view will be called
     * @param attrs attributes that are passed to the view
     */
    public CountryPickerView(Context context, AttributeSet attrs) {
        super(context,attrs);

        // Tell the surface view we want to create an OpenGL ES 2.0-compatible
        // context, and set an OpenGL ES 2.0-compatible renderer.
        this.setEGLContextClientVersion(2);
        // Request an 565 Color buffer with 16-bit depth and 0-bit stencil
        this.setEGLConfigChooser(5, 6, 5, 0, 16, 0);

        this.setEGLContextClientVersion(2);
        this.objPickerRenderer = new CountryPickerRenderer(context);
        this.setRenderer(objPickerRenderer);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        switch(event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                objPickerRenderer.onPress(x, y);
                break;
        }
        return super.onTouchEvent(event);
    }
}
