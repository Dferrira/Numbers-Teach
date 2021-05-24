package com.dferreira.numbers_teach.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dferreira.numbers_teach.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Image helper is responsible for handle the change of the images
 * that have strongly dependency from the API version
 */
public class ImageHelper {

    /**
     * Set the drawable of the image taking in account the platform which is running
     *
     * @param context    context where this method is called
     * @param resourceId resource id that will be used in the drawable
     * @return the drawable that was read
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(Context context, int resourceId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resourceId);
        } else {
            return context.getResources().getDrawable(resourceId, context.getTheme());
        }
    }


    /**
     * Set the background of the view
     *
     * @param view       The view that is going to get a new background
     * @param background The background to put in the view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundDrawable(View view, Drawable background) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(background);
        } else {
            view.setBackground(background);
        }
    }

    /**
     * @param view view
     * @return The drawable that represents the view
     */
    public static Drawable getDrawable(View view) {
        Drawable drawable = (view instanceof ImageView) ? ((ImageView) view).getDrawable() : view.getBackground();
        if (drawable == null) {
            return view.getBackground();
        } else {
            return drawable;
        }
    }

    /**
     * Create an array of drawables loaded from the assets
     *
     * @param context    Context with which is going to get the assets
     * @param imagesPath List of paths
     * @return Drawables in the specified paths
     */
    public static Drawable[] loadImages(Context context, String[] imagesPath) {
        Drawable[] drawables = new Drawable[imagesPath.length];
        for (int i = 0; i < imagesPath.length; i++) {
            InputStream imgStream = null;
            String imagePath = imagesPath[i];
            if ((!TextUtils.isEmpty(imagePath))) {
                try {
                    imgStream = context.getAssets().open(imagePath);
                    if (imgStream != null) {
                        Drawable drawable = Drawable.createFromStream(imgStream, null);
                        drawables[i] = drawable;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (imgStream != null) {
                        try {
                            imgStream.close();
                        } catch (IOException ignored) {

                        }
                    }
                }
            }
        }
        return drawables;
    }

    /**
     * Gets one drawable and the pixels that find that are transparent fill them with color passed
     *
     * @param drawableOriginal Drawable passed
     */
    public static LayerDrawable getCardBackground(Context context, Drawable drawableOriginal) {
        Drawable[] layers = new Drawable[2];
        layers[0] = context.getResources().getDrawable(R.drawable.shape_green_strong);
        layers[1] = drawableOriginal;
        return new LayerDrawable(layers);
    }
}
