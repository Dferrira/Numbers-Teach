package com.dferreira.numbers_teach.europe_dashboard;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.wavefront.WfRender;
import com.dferreira.numbers_teach.helpers.CameraHelper;
import com.dferreira.numbers_teach.sequence.SequenceActivity;
import com.dferreira.numbers_teach.ui_model.Europe3DData;
import com.dferreira.numbers_teach.ui_model.Obj3DData;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Render the map of Europe to pick a country
 */
class CountryPickerRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "CountryPickerRenderer";
    private static final float CAMERA_X = 2.0f;
    private static final float CAMERA_Y = 0.0f;
    private static final float CAMERA_Z = -5.0f;
    private static final float MODEL_ANGLE = 290.0f;
    /**
     * Handle to a program object
     */
    private final Context context;
    // Additional member variables
    private int mWidth;
    private int mHeight;

    private long mLastTime = 0;


    // ModelView-Perspective matrix
    private final GLTransformation mMVPMatrix;


    //Indicate if the user have clicked
    private boolean viewPressed;
    private int x, y;
    private WfRender selectableRender, textureRender;


    private Europe3DData europe3D;

    /**
     * Constructor
     *
     * @param context Context where the scene is going to be rendered
     */
    public CountryPickerRenderer(Context context) {
        this.context = context;
        this.viewPressed = false;
        this.mMVPMatrix = new GLTransformation();
    }

    /**
     * @param render  Render that is going to be used to render the objects in the
     * @param country data of the country to render
     */
    private static void setCountryScale(WfRender render, Obj3DData country) {
        if (country.isSelected()) {
            render.setScale(2.0f, 2.0f, 1.2f);
        } else {
            render.setScale(1.0f, 1.0f, 1.0f);
        }
    }

    /**
     * Load the programs shader and creates the renders (for color and for texture)
     */
    private void loadProgramsShader() {
        EuropeGLSL europeGLLoader = new EuropeGLSL(context, mMVPMatrix);
        this.selectableRender = europeGLLoader.loadSelectableProgram();
        this.textureRender = europeGLLoader.loadTexturedProgram();
    }

    /**
     * Initialize the shader and program object
     *
     * @param gl10   The GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     * @param config The EGLConfig of the created surface. Can be used to create matching pBuffers.
     */
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        loadProgramsShader();

        this.europe3D = new Europe3DData(context);
        this.europe3D.loadModel();


        GLES20.glClearColor(0.0f, 0.545f, 0.545f, 0.0f);

        // Set the depth clear value
        GLES20.glClearDepthf(0.75f);

        // Enable the depth and test
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

    }

    /**
     * Handle surface changes
     *
     * @param gl10   reference to open GL 1.0 library (not used)
     * @param width  width of the canvas where the the world will be draw
     * @param height height of the canvas where the the world will be draw
     */
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    /**
     * Count the milliseconds passed from the last frame render until now
     */
    private void framesCounter() {
        if (mLastTime == 0)
            mLastTime = SystemClock.uptimeMillis();
        long curTime = SystemClock.uptimeMillis();
        long elapsedTime = curTime - mLastTime;
        mLastTime = curTime;
        Log.d(TAG, "Time by frame" + (1000.0f / elapsedTime) + " frames/s");
    }

    /**
     * Configures the parameters of the camera
     * and sets the position of it
     */
    private void setCameraAndWorldTransformations() {
        GLTransformation perspective = new GLTransformation();
        GLTransformation modelView = new GLTransformation();


        CameraHelper.setCameraProperties(perspective, mWidth, mHeight);

        // Load the identity matrix
        modelView.glLoadIdentity();

        // Set the position of the camera
        modelView.glTranslate(CAMERA_X, CAMERA_Y, CAMERA_Z);

        // Rotate the scene
        modelView.glRotate(MODEL_ANGLE, 1.0f, 0.0f, 0.0f);

        // Compute the final MVP by multiplying the
        // modelView and gluPerspective matrices together
        mMVPMatrix.multiplyMatrix(modelView, perspective);
    }


    /**
     * Draw the objects in screen as a unique color to be able to track which one was clicked
     * by the user
     */
    private void drawSelectableFrame() {

        selectableRender.useProgramShader();
        for (int i = 0; i < europe3D.getCountries().length; i++) {
            Obj3DData country = europe3D.getCountries()[i];
            if (country == null) {
                continue;
            }
            CountryPickerRenderer.setCountryScale(selectableRender, country);

            selectableRender.drawUniObjColor(country.getWfObject(), country.getXPosition(), country.getYPosition(), country.getZPosition());

        }
    }

    /**
     * Draw the objects as a composition of textures
     */
    private void drawTexturedFrame() {

        textureRender.useProgramShader();
        for (int i = 0; i < europe3D.getCountries().length; i++) {
            Obj3DData country = europe3D.getCountries()[i];
            if (country == null) {
                continue;
            }
            CountryPickerRenderer.setCountryScale(textureRender, country);

            textureRender.drawObjTexture(country.getWfObject(), country.getXPosition(), country.getYPosition(), country.getZPosition());
        }

        //Draw the rest of the continent
        textureRender.setScale(1.0f, 1.0f, 1.0f);
        Obj3DData continent = europe3D.getContinent();
        textureRender.drawObjTexture(continent.getWfObject(), continent.getXPosition(), continent.getYPosition(), continent.getZPosition());
    }


    /**
     * Draw a triangle using the shader pair created in onSurfaceCreated()
     *
     * @param gl10 reference to open GL 1.0 library (not used)
     */
    @Override
    public void onDrawFrame(GL10 gl10) {

        framesCounter();

        setCameraAndWorldTransformations();

        // Set the viewport
        GLES20.glViewport(0, 0, mWidth, mHeight);


        // Use the program object
        if (viewPressed) {
            // Clear the color and depth buffers
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


            drawSelectableFrame();

            ByteBuffer pixel = ByteBuffer.allocateDirect(4);

            GLES20.glFlush();
            GLES20.glReadPixels(x, mHeight - y, 1, 1, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, pixel);

            int picked = 0x1f & (pixel.get(2) >> 3); //I am just using the 5 more signification bytes

            Object selectedTag = this.europe3D.detectCountrySelected(picked);
            startSequenceActivity(selectedTag);

            viewPressed = false;
        }

        // Clear the color and depth buffers
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);


        drawTexturedFrame();

    }

    /**
     * If there was one country that was selected if going to
     * @param selectedTag
     */
    private void startSequenceActivity(Object selectedTag) {
        if ((selectedTag != null) && (selectedTag instanceof  String)) {
            Intent sequenceIntent = new Intent(context, SequenceActivity.class);
            sequenceIntent.putExtra(SequenceActivity.COUNTRY_KEY, (String)selectedTag); //Optional parameters
            context.startActivity(sequenceIntent);

        }
    }


    /**
     * When the user presses the canvas this method will be trigger
     *
     * @param x x position that the user clicked
     * @param y y position that the user clicked
     */
    public void onPress(int x, int y) {
        this.viewPressed = true;
        this.x = x;
        this.y = y;
    }
}
