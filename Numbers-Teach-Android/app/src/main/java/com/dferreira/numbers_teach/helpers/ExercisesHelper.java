package com.dferreira.numbers_teach.helpers;


import java.util.Random;

/**
 * Helper to create the logic behind the exercise
 */
public class ExercisesHelper {

    private static final Random rnd = new Random();
    private static final int ARRAY_INIT_VALUE = -1;

    /**
     * @param iArray array o integers
     * @param value  value to check if exists
     * @return false : The array does not contain the value passed
     * true : The array contains the value that was passed
     */
    private static boolean arrayContains(int[] iArray, int value) {
        int arrayLength = iArray.length;
        for (int i = 0; i < arrayLength; i++) {
            if (iArray[i] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates an array of not repeated indexes
     *
     * @param min         Min value of the index
     * @param max         Max value of the index
     * @param totalSlides total of slides
     * @return Array of indexes
     */
    public static int[] generateIndexes(int min, int max, int totalSlides) {
        int maxNumber = max - min;
        int[] genIndexes = new int[totalSlides];

        //Avoid be trap by initialization of zeros
        for (int i = 0; i < genIndexes.length; i++) {
            genIndexes[i] = ARRAY_INIT_VALUE;
        }

        //Generate no repeated values
        for (int i = 0; i < genIndexes.length; i++) {
            int newValue;
            do {
                newValue = rnd.nextInt(maxNumber);
            } while (arrayContains(genIndexes, newValue));
            genIndexes[i] = newValue;
        }

        //Add the min value to set the values in the range
        for (int i = 0; i < genIndexes.length; i++) {
            genIndexes[i] = min + genIndexes[i];
        }

        return genIndexes;
    }

    /**
     * Generate an array of possible options to the user select the right one
     *
     * @param rightIndex      right index to the user to select
     * @param totalSlides     total of slides
     * @param numberOfOptions Number of options show to the user
     * @return Array of index of the selectable options
     */
    public static int[] generateIndexesOptions(int rightIndex, int totalSlides, int numberOfOptions) {

        int[] genIndexes = new int[numberOfOptions];

        //Avoid be trap by initialization of zeros
        for (int i = 0; i < genIndexes.length; i++) {
            genIndexes[i] = ARRAY_INIT_VALUE;
        }

        //Put the right value somewhere
        genIndexes[rnd.nextInt(numberOfOptions)] = rightIndex;

        //Generate no repeated values
        for (int i = 0; i < genIndexes.length; i++) {
            if (genIndexes[i] == ARRAY_INIT_VALUE) {
                int newValue;
                do {
                    newValue = rnd.nextInt(totalSlides);
                } while (arrayContains(genIndexes, newValue));
                genIndexes[i] = newValue;
            }
        }


        return genIndexes;

    }


    /**
     * @param index Index of the option
     * @return The tag corresponding to the index passed
     */
    public static Object getTag(int index) {
        return Integer.toString(index);
    }

    /**
     * @param indexes Array of indexes to generate the tags
     * @return Tags of the images to allow in the palette of images
     */
    public static Object[] getTags(int[] indexes) {
        if ((indexes == null) || (indexes.length <= 0)) {
            return null;
        } else {
            Object[] tags = new Object[indexes.length];
            for (int i = 0; i < indexes.length; i++) {

                tags[i] = getTag(indexes[i]);
            }
            return tags;
        }
    }

}
