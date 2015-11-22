package com.dferreira.commons;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Set of util methods to help load the application
 */
public class LoadUtils {

    private final static String TAG = "LoadUtils";

    /**
     * Load texture from resource
     *
     * @param context    Context where this method will be called
     * @param resourceId id of the resource where the texture exists
     * @return Id from the texture that was bounded in openGL
     */

    public static int loadTexture(Context context, int resourceId) {
        int[] textureId = new int[1];
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        byte[] buffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 3];

        for (int y = 0; y < bitmap.getHeight(); y++)
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int pixel = bitmap.getPixel(x, y);
                int offsetBase = ((y * bitmap.getWidth() + x) * 3);
                buffer[offsetBase] = (byte) ((pixel >> 16) & 0xFF);
                buffer[offsetBase + 1] = (byte) ((pixel >> 8) & 0xFF);
                buffer[offsetBase + 2] = (byte) ((pixel) & 0xFF);
            }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 3);
        byteBuffer.put(buffer).position(0);

        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, bitmap.getWidth(), bitmap.getHeight(), 0,
                GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, byteBuffer);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        return textureId[0];
    }

    /**
     * Returns if openGL 2.0 exists in device or not
     *
     * @param context Context where this method will be called
     * @return false -> There are no GLes v2.0 available into device
     * true  -> Exist GLes v2.0 into device
     */
    public static boolean detectOpenGLES20(Context context) {
        ActivityManager am =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }

    /**
     * Reads a string from a certain resource
     *
     * @param context       Context where this method will be called
     * @param rawResourceId id of the resource where the text exists
     * @return The text that exists in the resource
     */
    public static String readTextFromRawResource(Context context, int rawResourceId) {
        InputStream inputStream = context.getResources().openRawResource(rawResourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String str = null;

        int bytesRead;
        try {
            bytesRead = inputStream.read();
            while (bytesRead != -1) {
                byteArrayOutputStream.write(bytesRead);
                bytesRead = inputStream.read();
            }
            inputStream.close();
            str = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Read string failed", e);
        }
        return str;
    }
}
