package com.dferreira.numbers_teach.helpers;

import com.dferreira.commons.GLTransformation;

/**
 * Helps to set the properties of the camera
 */
public class CameraHelper {

    /**
     * Set the properties of the camera
     * @param perspective matrix that is going to be set
     * @param width width of the canvas
     * @param height height of the canvas
     */
    public static void setCameraProperties(GLTransformation perspective, int width, int height){
        float aspect;

        // Compute the window aspect ratio
        aspect = (float) width / (float) height;

        // Generate a gluPerspective matrix with a 60 degree FOV
        perspective.glLoadIdentity();
        perspective.gluPerspective(60.0f, aspect, 4.0f, 1000.0f);
    }
}
