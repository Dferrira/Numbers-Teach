package com.dferreira.numbers_teach.generic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

import java.io.InputStream;

/**
 * Stretch the number image to show correctly in any device
 */
public class DeviceStretcher {
    private final Context context;
    private Matrix matrix;

    private static final int HEIGHT_DISPLAY_BASE = 850;

    /**
     * Initializes the matrix that will stretch the images
     *
     * @param heightDevice Height from the current device
     */
    private void initializeMatrixTransformation(int heightDevice) {
        float zoom;

        zoom = (float) ((1.0 * heightDevice) / HEIGHT_DISPLAY_BASE);
        matrix = new Matrix();
        matrix.postScale(zoom, zoom);
    }

    /**
     * @return the width from the current device
     */
    private int getDisplayHeight() {
        WindowManager windowManager;
        Display display;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        //noinspection deprecation
        return display.getHeight();
    }


    public DeviceStretcher(Context context) {
        int heightDevice;

        this.context = context;
        heightDevice = getDisplayHeight();
        initializeMatrixTransformation(heightDevice);
    }

    /**
     * @param imgStream Stream of the file that points to the original image original draw image
     * @return image stretched
     */
    public Drawable zoomDrawable(InputStream imgStream) {
        Bitmap originalBitmap = null;

        try {
            if (imgStream == null) {
                return null;
            }
            originalBitmap = BitmapFactory.decodeStream(imgStream);

            Bitmap scaledBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
            return new BitmapDrawable(context.getResources(), scaledBitmap);
        } finally {
            if (originalBitmap != null) {
                originalBitmap.recycle();
            }
        }
    }
}