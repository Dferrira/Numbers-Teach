package com.dferreira.numbers_teach.numbers.models

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.dferreira.numbers_teach.commons.IGenericStudySet
import java.util.*

/**
 * Provide the necessary methods to access the data of the languages them selves
 */
class StudySet(
    private val context: Context
) : IGenericStudySet {


    /**
     * @return List of the numbers in english ready to be teach to the user
     */
    private fun getResourcePrefixes(): Array<String> {
        return arrayOf(
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen",
            "twenty"
        )
    }

    override fun getAudioLabel(language: String, index: Int): String {
        return getAudioLabel(language, index) {
            getResourcePrefixes()[it]
        }
    }

    /**
     * Get the label associated with parameters passed
     *
     * @param language language which will be used to produce the label
     * @param index    index of the audio that will be return
     * @return String that represents that audio
     */
    private fun getAudioLabel(language: String, index: Int, getResourceNameFunction: (Int) -> String): String {
        val conf = context.resources.configuration
        val original = conf.locale
        conf.locale = Locale(language)
        val metrics = DisplayMetrics()
        val resources = Resources(context.assets, metrics, conf)
        val resourceName = getResourceNameFunction(index)
        val resourceId =
            context.resources.getIdentifier(resourceName, "string", context.packageName)
        /* get localized string */
        val label = resources.getString(resourceId)
        conf.locale = original
        resources.updateConfiguration(conf, metrics)
        return label
    }

    override fun getCounter(language: String): Int {
        return getResourcePrefixes().size
    }

    /**
     * @param index Index of the image to show
     * @return The name of the png image associated
     */
    private fun getImageFileName(index: Int): String {
        return getResourcePrefixes()[index] + IMG2D_FILE_SUFFIX
    }

    private fun getAudioFolder(language: String): String? {
        return AUDIO_FOLDER_PREFIX + language
    }

    private fun getAudioFileName(index: Int): String? {
        return getResourcePrefixes()[index] + AUDIO_FILE_SUFFIX
    }

    override fun getAudioPath(language: String, index: Int): String {
        return getAudioFolder(language)+ "/" + getAudioFileName(index)
    }

    override fun getImagePath(index: Int): String {
        return PICTURES_FOLDER + "/" + getImageFileName(index)
    }

    override fun getImagesPath(indexes: IntArray): Array<String> {
        return if (indexes.isEmpty()) {
            emptyArray()
        } else {
            indexes.map { index -> getImagePath(index) }
                .toTypedArray()
        }
    }

    companion object {
        private const val AUDIO_FOLDER_PREFIX = "audio-"
        private const val PICTURES_FOLDER = "pictures"
        private const val AUDIO_FILE_SUFFIX = ".mp3"
        private const val IMG2D_FILE_SUFFIX = ".png"
    }
}