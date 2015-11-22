package com.dferreira.commons.wavefront;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Describes the information of a waveFront file
 */
public class WfObject implements Serializable {


    @SuppressWarnings("FieldCanBeLocal")
    private final int BYTES_BY_FLOAT = 4;
    public final static int COORDINATES_BY_VERTEX = 3;
    public final static int COORDINATES_BY_TEXTURE = 2;
    public final static int COORDINATES_BY_NORMAL = 3;


    /**
     * Buffer that supports the vertices of the object
     */
    private final FloatBuffer vertices;

    /**
     * Buffer that supports the coordinates of the textures of the object
     */
    private final FloatBuffer texCoordinates;

    /**
     * Buffer that supports the normal of the object
     */
    private final FloatBuffer normals;

    /**
     * Buffer that supports the indexes of the object
     */
    private final ShortBuffer indexes;

    /**
     * Number of faces that the object has
     */
    private final int numFaces;

    /**
     * Value that is going to be used to uniquely identify the object
     */
    private final int objCode;

    /**
     * Texture id of the object
     */
    private final int textureId;

    /**
     * @param numFaces Number of faces that compose this object 3D
     *                 Creates an object with ability handle the number of faces passed
     *
     * @param objCode  Value that is going to be used to uniquely identify the object
     * @param textureId Id of the texture associated with wavefront object
     */
    public WfObject(int numFaces, int objCode,int textureId) {
        this.numFaces = numFaces;
        this.objCode = objCode;
        this.textureId = textureId;

        ByteBuffer vbb = ByteBuffer.allocateDirect(numFaces * BYTES_BY_FLOAT * COORDINATES_BY_VERTEX);
        vbb.order(ByteOrder.nativeOrder());
        this.vertices = vbb.asFloatBuffer();

        ByteBuffer textureCoordinatesBb = ByteBuffer.allocateDirect(numFaces * BYTES_BY_FLOAT * COORDINATES_BY_TEXTURE);
        textureCoordinatesBb.order(ByteOrder.nativeOrder());
        this.texCoordinates = textureCoordinatesBb.asFloatBuffer();

        ByteBuffer nbb = ByteBuffer.allocateDirect(numFaces * BYTES_BY_FLOAT * COORDINATES_BY_NORMAL);
        nbb.order(ByteOrder.nativeOrder());
        this.normals = nbb.asFloatBuffer();

        this.indexes = ShortBuffer.allocate(numFaces);
    }

    public void putVertexCoordinate(float coordinateValue) {
        this.vertices.put(coordinateValue);
    }

    public void putTextureCoordinate(float coordinateValue) {
        this.texCoordinates.put(coordinateValue);
    }

    public void putNormalCoordinate(float coordinateValue) {
        this.normals.put(coordinateValue);
    }

    public void putIndex(short index) {
        indexes.put(index);
    }

    /**
     * Set the current position of all the buffers to 0
     */
    public void resetBuffersPosition() {
        vertices.position(0);
        texCoordinates.position(0);
        normals.position(0);
        indexes.position(0);
    }

    /**
     *
     * @return the number of faces that compose the 3D object
     */
    public int getNumFaces() {
        return numFaces;
    }

    public ShortBuffer getIndices() {
        return indexes;
    }

    public FloatBuffer getVertices() {
        return vertices;
    }

    public FloatBuffer getTexCoordinates() {
        return texCoordinates;
    }

    public int getObjCode() {
        return objCode;
    }

    public int getTextureId() {
        return textureId;
    }


}
