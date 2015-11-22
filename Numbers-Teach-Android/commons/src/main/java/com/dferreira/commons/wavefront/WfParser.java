package com.dferreira.commons.wavefront;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dferreira.commons.LoadUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Daniel on 05/11/2015.
 * Parses a file formatted in Wavefront
 * reference : https://en.wikipedia.org/wiki/Wavefront_.obj_file
 */
public class WfParser implements Serializable {

    private static final String VERTEX_PREFIX = "v";
    private static final String NORMAL_PREFIX = "vn";
    private static final String TEXTURE_PREFIX = "vt";
    private static final String FACE_PREFIX = "f";
    private static final String TAG = "WfParser";

    private static final int MAX_OBJ_CODES = 100;
    private int objCode;
    private final Context context;

    public WfParser(Context context) {
        this.objCode = 1;
        this.context = context;
    }

    private int getNewObjCode() {
        if(objCode >= MAX_OBJ_CODES) {
            objCode = 1;
        }
        return objCode++;
    }

    /**
     * Parse a component in string format and return its long value
     *
     * @param strComponent Component to parse
     * @return Long value or null if it is the case
     */
    private static Long ParseLongComponent(String strComponent) {
        if (TextUtils.isEmpty(strComponent)) {
            return null;
        } else {
            return Long.parseLong(strComponent) - 1;
        }
    }

    /**
     * Parses a wavefront object
     *
     * @param inputStream input stream to the file where should read the description of the object
     *
     * @return Wavefront object
     */
    public WfObject loadObj(InputStream inputStream, int textureResourceId) {
        WfObject wfObject = null;

        try {
            String str;
            String[] tmp;
            String[] faceStr;
            float v;
            ArrayList<Float> verticesLst = new ArrayList<>();
            ArrayList<Float> textureCoordinatesLst = new ArrayList<>();
            ArrayList<Float> vertexNormalsLst = new ArrayList<>();
            ArrayList<PolygonalFace> facesLst = new ArrayList<>();


            BufferedReader inb = new BufferedReader(new InputStreamReader(inputStream), 1024);
            while ((str = inb.readLine()) != null) {
                tmp = str.split(" ");
                switch (tmp[0].toLowerCase()) {
                    //Parses the vertexes
                    case VERTEX_PREFIX:
                        for (int i = 1; i < 4; i++) {
                            v = Float.parseFloat(tmp[i]);
                            verticesLst.add(v);
                        }
                        break;
                    //Parses the normals
                    case NORMAL_PREFIX:
                        for (int i = 1; i < 4; i++) {
                            v = Float.parseFloat(tmp[i]);
                            vertexNormalsLst.add(v);
                        }
                        break;
                    //Parses the texture coordinates
                    case TEXTURE_PREFIX:
                        for (int i = 1; i < 3; i++) {
                            v = Float.parseFloat(tmp[i]);
                            textureCoordinatesLst.add(v);
                        }
                        break;
                    //Parses the face
                    case FACE_PREFIX:
                        for (int i = 1; i < 4; i++) {
                            faceStr = tmp[i].split("/");

                            Long vertexIndex = ParseLongComponent(faceStr[0]);
                            Long textureIndex = ParseLongComponent(faceStr[1]);
                            Long normalIndex = ParseLongComponent(faceStr[2]);

                            facesLst.add(new PolygonalFace(vertexIndex, textureIndex, normalIndex));
                        }
                        break;
                }
            }

            int textureId = LoadUtils.loadTexture(context, textureResourceId);
            wfObject = new WfObject(facesLst.size(), getNewObjCode(), textureId);

            for (int j = 0; j < facesLst.size(); j++) {
                //Uses the (faces and vertices list to build the final vertices buffer
                wfObject.putVertexCoordinate(verticesLst.get((int) (facesLst.get(j).getVertexIndex() * WfObject.COORDINATES_BY_VERTEX)));
                wfObject.putVertexCoordinate(verticesLst.get((int) (facesLst.get(j).getVertexIndex() * WfObject.COORDINATES_BY_VERTEX + 1)));
                wfObject.putVertexCoordinate(verticesLst.get((int) (facesLst.get(j).getVertexIndex() * WfObject.COORDINATES_BY_VERTEX + 2)));

                //Build the texture coordinates list
                if (facesLst.get(j).getTextureIndex() != null) {
                    wfObject.putTextureCoordinate(textureCoordinatesLst.get((int) (facesLst.get(j).getTextureIndex() * WfObject.COORDINATES_BY_TEXTURE)));
                    wfObject.putTextureCoordinate(textureCoordinatesLst.get((int) ((facesLst.get(j).getTextureIndex() * WfObject.COORDINATES_BY_TEXTURE) + 1)));
                }

                //Build the normals list
                wfObject.putNormalCoordinate(vertexNormalsLst.get((int) (facesLst.get(j).getNormalIndex() * WfObject.COORDINATES_BY_NORMAL)));
                wfObject.putNormalCoordinate(vertexNormalsLst.get((int) ((facesLst.get(j).getNormalIndex() * WfObject.COORDINATES_BY_NORMAL) + 1)));
                wfObject.putNormalCoordinate(vertexNormalsLst.get((int) ((facesLst.get(j).getNormalIndex() * WfObject.COORDINATES_BY_NORMAL) + 2)));

                //Build index lists
                wfObject.putIndex((short) j);
            }

            wfObject.resetBuffersPosition();


        } catch (IOException e) {
            Log.e(TAG, "loadObj failed", e);
        }

        return wfObject;
    }
}