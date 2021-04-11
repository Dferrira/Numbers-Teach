package com.dferreira.numbers_teach.commons

import android.content.Context

interface IGenericStudySet {
    fun getAudioLabel(language: String?, index: Int): String

    /**
     * @return Number of images available to show to the user
     */
    fun getNumberCount(): Int

    /**
     * @param language Language that the user is learning and want to see
     * @param index    Index of the image to show
     * @return The path of the audio to be played taking in account the passed arguments
     */
    fun getAudioPath(language: String, index: Int): String

    /**
     * @param index Index of the image to show
     * @return The path of the image 2D to be show to the user taking in account the passed arguments
     */
    fun getImagePath(index: Int): String

    /**
     * @param indexes Index of the images to show
     * @return The paths of the images to be show to the user taking in account the passed arguments
     */
    fun getImagesPath(indexes: IntArray?): Array<String>
}