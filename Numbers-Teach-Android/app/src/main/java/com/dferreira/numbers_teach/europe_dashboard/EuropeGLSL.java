package com.dferreira.numbers_teach.europe_dashboard;

import android.content.Context;
import android.opengl.GLES20;

import com.dferreira.commons.GLSLUtils;
import com.dferreira.commons.GLTransformation;
import com.dferreira.commons.LoadUtils;
import com.dferreira.commons.wavefront.WfRender;
import com.dferreira.numbers_teach.R;

/**
 * Load the GL shader programs
 */
@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class EuropeGLSL {

    private final String U_MVP_MATRIX_KEY = "u_mvpMatrix";
    private final String X_OFFSET_KEY = "x_offset";
    private final String Y_OFFSET_KEY = "y_offset";
    private final String Z_OFFSET_KEY = "z_offset";
    private final String X_SCALE_KEY = "x_scale";
    private final String Y_SCALE_KEY = "y_scale";
    private final String Z_SCALE_KEY = "z_scale";
    private final String OBJ_CODE_KEY = "obj_code";
    private final String A_POSITION_KEY = "a_position";
    private final String A_TEX_COORDINATE_KEY = "a_tex_coordinate";


    // ModelView-Perspective matrix
    private final GLTransformation mMVPMatrix;

    //Strings of GL-SL
    private final String vShaderStr;
    private final String fShaderTexturedStr;
    private final String fShaderColoredStr;


    public EuropeGLSL(Context context, GLTransformation mMVPMatrix){
        this.mMVPMatrix = mMVPMatrix;
        this.vShaderStr = LoadUtils.readTextFromRawResource(context, R.raw.vertex_shader);
        this.fShaderTexturedStr = LoadUtils.readTextFromRawResource(context, R.raw.fragment_shader_textured);
        this.fShaderColoredStr = LoadUtils.readTextFromRawResource(context, R.raw.fragment_shader_colored);
    }

    /**
     * Used in both program shader loaders
     *
     * @param mProgramObject Id of the shader program
     *
     * @return the render to that program
     */
    private WfRender genericLoader(int mProgramObject)
    {
        // Get the attribute locations
        int mPositionLoc = GLES20.glGetAttribLocation(mProgramObject, A_POSITION_KEY);


        // Get the uniform locations
        int mMVPLoc = GLES20.glGetUniformLocation(mProgramObject, U_MVP_MATRIX_KEY);



        // Get the offset location
        int xOffsetLoc = GLES20.glGetUniformLocation(mProgramObject, X_OFFSET_KEY);
        int yOffsetLoc = GLES20.glGetUniformLocation(mProgramObject, Y_OFFSET_KEY);
        int zOffsetLoc = GLES20.glGetUniformLocation(mProgramObject, Z_OFFSET_KEY);

        //Get the scale po
        int xScaleLoc = GLES20.glGetUniformLocation(mProgramObject, X_SCALE_KEY);
        int yScaleLoc = GLES20.glGetUniformLocation(mProgramObject, Y_SCALE_KEY);
        int zScaleLoc = GLES20.glGetUniformLocation(mProgramObject, Z_SCALE_KEY);

        return new WfRender(mProgramObject, mMVPMatrix, mPositionLoc, mMVPLoc, xOffsetLoc, yOffsetLoc, zOffsetLoc, xScaleLoc, yScaleLoc, zScaleLoc);
    }

    /**
     *
     * @return Load the program shader to show the countries texturize
     */
    public WfRender loadTexturedProgram() {

        // Load the shader and get a linked program object
        int mProgramObject = GLSLUtils.loadProgram(vShaderStr, fShaderTexturedStr);
        WfRender textureRender = genericLoader(mProgramObject);

        int mTexCoordinateLoc = GLES20.glGetAttribLocation(mProgramObject, A_TEX_COORDINATE_KEY);
        textureRender.setTexCoordinateAttrId(mTexCoordinateLoc);

        return textureRender;
    }

    /**
     * @return Load the program shader to select the countries
     */
    public WfRender loadSelectableProgram() {

        // Load the shader and get a linked program object
        int mProgramObject = GLSLUtils.loadProgram(vShaderStr, fShaderColoredStr);


        WfRender selectableRender = genericLoader(mProgramObject);
        //Unique identifier of an object
        int objCode = GLES20.glGetUniformLocation(mProgramObject, OBJ_CODE_KEY);
        selectableRender.setObjCodeAttrId(objCode);

        return selectableRender;
    }

}
