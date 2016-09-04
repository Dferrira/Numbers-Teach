package com.dferreira.numbers_teach.generic_exercise;


/**
 * Interface that should be implemented by the images palette in the UI
 */
public interface IImagesPalette {

    /**
     * Set the drawables to set in the palette
     *
     * @param paths of the drawables to show in the palette
     */
    void setDrawablesPaths(String[] paths);

    /**
     * Set the tags of the image views
     *
     * @param tags Tags of the images
     */
    void setTags(Object[] tags);

    /**
     * Set the view that is going to be set when the user click in one image to choose in
     * the palette
     *
     * @param target View that is going to get the selected option
     */
    void setTarget(ISelectedChoice target);
}
