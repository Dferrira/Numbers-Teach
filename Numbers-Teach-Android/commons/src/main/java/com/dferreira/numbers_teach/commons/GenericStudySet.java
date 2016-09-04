package com.dferreira.numbers_teach.commons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Generic class with some methods to access the different components of the set of resources
 */
public abstract class GenericStudySet {

    /**
     * @return A list with prefixes of the resources provides by the repository
     */
    protected abstract String[] getResourcePrefixes();

    /**
     * Get the label associated with parameters passed
     *
     * @param context  context where this method is called
     * @param language language which will be used to produce the label
     * @param index    index of the audio that will be return
     * @return String that represents that audio
     */
    public String getAudioLabel(Context context, String language, int index) {
        Configuration conf = context.getResources().getConfiguration();
        Locale original = conf.locale;
        conf.locale = new Locale(language);
        DisplayMetrics metrics = new DisplayMetrics();
        Resources resources = new Resources(context.getAssets(), metrics, conf);
        String resourceName = getResourcePrefixes()[index];
        int resourceId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
        /* get localized string */
        String label = resources.getString(resourceId);
        conf.locale = original;
        resources.updateConfiguration(conf, metrics);
        return label;
    }


    private static final String AUDIO_FOLDER_PREFIX = "audio-";
    private static final String PICTURES_FOLDER = "pictures";

    private static final String AUDIO_FILE_SUFFIX = ".mp3";
    private static final String IMG2D_FILE_SUFFIX = ".png";


    /**
     * @return Number of images available to show to the user
     */
    public int getNumberCount() {
        return getResourcePrefixes().length;
    }

    /**
     * @param language Language that the user is learning and want to see
     * @return Name of the folder where the audio exists to the specified language
     */
    private String getAudioFolder(String language) {
        return AUDIO_FOLDER_PREFIX + language;
    }

    /**
     * @param index Index of the audio file to play
     * @return Name of the file where the audio exist to the specified language
     */
    private String getAudioFile(int index) {
        return getResourcePrefixes()[index] + AUDIO_FILE_SUFFIX;
    }

    /**
     * @param index Index of the image to show
     * @return The name of the png image associated
     */
    private String getImageFile(int index) {
        return getResourcePrefixes()[index] + IMG2D_FILE_SUFFIX;
    }


    /**
     * @param language Language that the user is learning and want to see
     * @param index    Index of the image to show
     * @return The path of the audio to be played taking in account the passed arguments
     */
    public String getAudioPath(String language, int index) {
        return getAudioFolder(language) + "/" + getAudioFile(index);
    }

    /**
     * @param index Index of the image to show
     * @return The path of the image 2D to be show to the user taking in account the passed arguments
     */
    public String getImagePath(int index) {
        return PICTURES_FOLDER + "/" + getImageFile(index);
    }

    /**
     * @param indexes Index of the images to show
     * @return The paths of the images to be show to the user taking in account the passed arguments
     */
    public String[] getImagesPath(int[] indexes) {
        if ((indexes == null) || (indexes.length <= 0)) {
            return null;
        } else {
            String[] paths = new String[indexes.length];
            for (int i = 0; i < indexes.length; i++) {
                paths[i] = getImagePath(indexes[i]);
            }
            return paths;
        }
    }
}
