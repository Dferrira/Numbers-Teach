package com.dferreira.commons.wavefront;

import android.opengl.GLES20;

import com.dferreira.commons.GLTransformation;

/**
 * Able to render objects in a specified position of the 3D world
 */
public class WfRender {

    private final int VERTEX_SIZE = 3;
    //Offset between following vertices
    private final int STRIDE = 0;
    //Specifies whether fixed-point data values should be normalized (GL_TRUE) or converted directly as fixed-point values (GL_FALSE) when they are accessed.
    private final boolean VERTEX_NORMALIZED = false;

    //number of components per generic vertex attribute
    @SuppressWarnings("FieldCanBeLocal")
    private final int NUMBER_COMPONENTS_PER_VERTEX_ATTR = 2;

    //Id of the program shader that is going to be used to render
    private final int mProgramObject;

    private final int locationAttrId;
    // Uniform locations pointers to the values defined in the vertex shader
    private final int mMVPLocAttrId;
    private final int xOffsetLocAttrId;
    private final int yOffsetLocAttrId;
    private final int zOffsetLocAttrId;
    //Uniform scale pointer to the value defined in the vertex shader
    private final int xScaleAttrId;
    private final int yScaleAttrId;
    private final int zScaleAttrId;



    private int objCodeAttrId;
    private int texCoordinateAttrId;


    //Model view perspective matrix
    private final GLTransformation mMVPMatrix;


    //Current scale
    @SuppressWarnings({"SameParameterValue", "SuspiciousNameCombination"})
    public void setScale(float xScale, float yScale, float zScale) {
        GLES20.glUniform1f(xScaleAttrId, xScale);
        GLES20.glUniform1f(yScaleAttrId, yScale);
        GLES20.glUniform1f(zScaleAttrId, zScale);
    }


    /**
     * @param mProgramObject Program shader that is going to be used by the render
     * @param mMVPMatrix model-view-perspective matrix
     * @param locationAttrId place where the positions of the object are going to be set
     * @param mMVPLocAttrId Id of the model-view-perspective matrix
     * @param xOffsetLocAttrId Id of the x offset attribute
     * @param yOffsetLocAttrId Id of the y offset attribute
     * @param zOffsetLocAttrId Id of the z offset attribute
     * @param xScaleAttrId Id of the x scale attribute
     * @param yScaleAttrId Id of the y scale attribute
     * @param zScaleAttrId Id of the z scale attribute
     */
    public WfRender(int mProgramObject, GLTransformation mMVPMatrix, int locationAttrId, int mMVPLocAttrId, int xOffsetLocAttrId, int yOffsetLocAttrId, int zOffsetLocAttrId,
                    int xScaleAttrId, int yScaleAttrId, int zScaleAttrId
    ) {
        this.mProgramObject = mProgramObject;
        this.mMVPMatrix = mMVPMatrix;
        this.locationAttrId = locationAttrId;
        this.mMVPLocAttrId = mMVPLocAttrId;
        this.xOffsetLocAttrId = xOffsetLocAttrId;
        this.yOffsetLocAttrId = yOffsetLocAttrId;
        this.zOffsetLocAttrId = zOffsetLocAttrId;
        this.xScaleAttrId = xScaleAttrId;
        this.yScaleAttrId = yScaleAttrId;
        this.zScaleAttrId = zScaleAttrId;
    }

    /**
     *Set the coordinate of the object
     *
     * @param xOffsetLoc location offset coordinate x
     * @param yOffsetLoc location offset coordinate y
     * @param zOffsetLoc location offset coordinate z
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void setObjectCoordinates(float xOffsetLoc, float yOffsetLoc, float zOffsetLoc) {
        GLES20.glUniform1f(xOffsetLocAttrId, xOffsetLoc);
        GLES20.glUniform1f(yOffsetLocAttrId, yOffsetLoc);
        GLES20.glUniform1f(zOffsetLocAttrId, zOffsetLoc);
    }

    /**
     * Draw the object in a single color
     *
     * @param wfObj wavefront object to draw
     * @param xOffsetLoc location offset coordinate x
     * @param yOffsetLoc location offset coordinate y
     * @param zOffsetLoc location offset coordinate z
     */
    public void drawUniObjColor(WfObject wfObj, float xOffsetLoc, float yOffsetLoc, float zOffsetLoc) {
        GLES20.glVertexAttribPointer(locationAttrId, VERTEX_SIZE, GLES20.GL_FLOAT, VERTEX_NORMALIZED, STRIDE, wfObj.getVertices());
        GLES20.glEnableVertexAttribArray(locationAttrId);

        // Load the MVP matrix
        GLES20.glUniformMatrix4fv(mMVPLocAttrId, 1, false, mMVPMatrix.getAsFloatBuffer());


        GLES20.glEnableVertexAttribArray(locationAttrId);

        //----------------------------
        setObjectCoordinates(xOffsetLoc, yOffsetLoc, zOffsetLoc);
        GLES20.glUniform4f(objCodeAttrId, 0.0f, 0.0f, wfObj.getObjCode() / 31.0f, 1.0f);

        //----------------------------
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, wfObj.getNumFaces(),
                GLES20.GL_UNSIGNED_SHORT, wfObj.getIndices());

    }

    /**
     * Draw the object textured
     *
     * @param wfObj wavefront object to draw
     * @param xOffsetLoc location offset coordinate x
     * @param yOffsetLoc location offset coordinate y
     * @param zOffsetLoc location offset coordinate z
     */
    public void drawObjTexture(WfObject wfObj, float xOffsetLoc, float yOffsetLoc, float zOffsetLoc) {
        // Load the MVP matrix
        GLES20.glUniformMatrix4fv(mMVPLocAttrId, 1, false, mMVPMatrix.getAsFloatBuffer());

        //----------------------------
        // Load the vertex data
        GLES20.glVertexAttribPointer(locationAttrId, VERTEX_SIZE, GLES20.GL_FLOAT, VERTEX_NORMALIZED, STRIDE, wfObj.getVertices());
        GLES20.glEnableVertexAttribArray(locationAttrId);

        // Load the texture coordinate

        GLES20.glVertexAttribPointer(texCoordinateAttrId, NUMBER_COMPONENTS_PER_VERTEX_ATTR, GLES20.GL_FLOAT,
                VERTEX_NORMALIZED,
                STRIDE,
                wfObj.getTexCoordinates());
        GLES20.glEnableVertexAttribArray(texCoordinateAttrId);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, wfObj.getTextureId());


        setObjectCoordinates(xOffsetLoc, yOffsetLoc, zOffsetLoc);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, wfObj.getNumFaces(),
                GLES20.GL_UNSIGNED_SHORT, wfObj.getIndices());
    }


    /**
     * @param objCodeAttrId Set the attribute id of the object code
     */
    public void setObjCodeAttrId(int objCodeAttrId) {
        this.objCodeAttrId = objCodeAttrId;
    }

    /**
     * @param texCoordinateAttrId  Set the attribute id of the texture coordinate
     */
    public void setTexCoordinateAttrId(int texCoordinateAttrId) {
        this.texCoordinateAttrId = texCoordinateAttrId;
    }

    /**
     *  Enables the use of the program shader
     */
    public void useProgramShader() {
        GLES20.glUseProgram(mProgramObject);
    }

}
