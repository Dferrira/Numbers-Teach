package com.dferreira.numbers_teach.ui_model;

import com.dferreira.commons.wavefront.WfObject;

import java.io.Serializable;

/**
 * Supports the positioning, selection and put texture over an object
 */
public class Obj3DData implements Serializable {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -4453723803430672590L;


    private WfObject wfObject;
    private float xPosition;
    private float yPosition;
    private float zPosition;
    private boolean selected;
    private Object tag;

    public WfObject getWfObject() {
        return wfObject;
    }

    public void setWfObject(WfObject wfObject) {
        this.wfObject = wfObject;
    }

    public float getXPosition() {
        return xPosition;
    }


    public float getYPosition() {
        return yPosition;
    }


    public float getZPosition() {
        return zPosition;
    }

    /**
     * The the position of the object
     * @param xPosition X position of the object
     * @param yPosition Y position of the object
     * @param zPosition Z position of the object
     */
    @SuppressWarnings("SameParameterValue")
    public void setPosition(float xPosition, float yPosition, float zPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
    }


    /**
     * @return  false -> the object was not selected
     *          true  -> the object was selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * indicate if the object was or not clicked by the user
     *
     * @param selected the value to set in the selected variable
     */
    @SuppressWarnings("SameParameterValue")
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the tag that was associated with this object
     */
    public Object getTag() {
        return tag;
    }

    /**
     * Set a tag for this object
     *
     * @param tag that is going to be used in this 3D object
     */
    public void setTag(Object tag) {
        this.tag = tag;
    }
}
