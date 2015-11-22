package com.dferreira.commons;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Allow to load a shader program (fragment and vertex shader)
 */
public class GLSLUtils {

    private final static String TAG = "GLSLUtils";

    /**
     * Load and compiles the shader into device memory
     *
     * @param type      Type of shader (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
     * @param shaderSrc a string with source code of the shader to compile
     * @return 0 -> There was an error
     * not 0 -> Id of the shader compiled
     */
    private static int compileShader(int type, String shaderSrc) {
        int shaderId;
        int[] compiled = new int[1];

        // Create the shader object
        shaderId = GLES20.glCreateShader(type);

        if (shaderId == 0)
            return 0;

        // Load the shader source
        GLES20.glShaderSource(shaderId, shaderSrc);

        // Compile the shader
        GLES20.glCompileShader(shaderId);

        // Check the compile status
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compiled, 0);


        if (compiled[0] == 0) {
            Log.e(TAG, GLES20.glGetShaderInfoLog(shaderId));
            GLES20.glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }


    /**
     * @param vertexShaderSrc Source code of the vertex shader
     * @param fragShaderSrc   Source code of the fragment shader
     * @return 0 -> There was an error
     * not 0 -> Id of the program loaded
     */
    public static int loadProgram(String vertexShaderSrc, String fragShaderSrc) {
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        // Load the vertex/fragment shader(s)
        vertexShader = compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderSrc);
        if (vertexShader == 0)
            return 0;

        fragmentShader = compileShader(GLES20.GL_FRAGMENT_SHADER, fragShaderSrc);
        if (fragmentShader == 0) {
            GLES20.glDeleteShader(vertexShader);
            return 0;
        }

        // Create the program object
        programObject = GLES20.glCreateProgram();

        if (programObject == 0)
            return 0;

        GLES20.glAttachShader(programObject, vertexShader);
        GLES20.glAttachShader(programObject, fragmentShader);

        // Link the program
        GLES20.glLinkProgram(programObject);

        // Check the link status
        GLES20.glGetProgramiv(programObject, GLES20.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES20.glGetProgramInfoLog(programObject));
            GLES20.glDeleteProgram(programObject);
            return 0;
        } else {
            // Free up no longer needed shader resources
            GLES20.glDeleteShader(vertexShader);
            GLES20.glDeleteShader(fragmentShader);

            return programObject;
        }
    }

}