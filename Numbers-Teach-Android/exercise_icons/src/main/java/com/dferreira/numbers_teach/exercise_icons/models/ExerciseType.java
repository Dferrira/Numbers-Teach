package com.dferreira.numbers_teach.exercise_icons.models;

import java.io.Serializable;

/**
 * Structure that indicates which kind of exercise it is
 */
public class ExerciseType implements Serializable {

    private final boolean withPicture;
    private final boolean withAudio;
    private final boolean withText;
    private final Integer exerciseIcon;

    /**
     * Constructor of the type of exercise
     *
     * @param withPicture Flag that indicates if has picture or not
     * @param withAudio   Flag that indicates if has audio or not
     * @param withText    Flag that indicates if has text or not
     */
    public ExerciseType(boolean withPicture, boolean withAudio, boolean withText, Integer exerciseIcon) {
        this.withPicture = withPicture;
        this.withAudio = withAudio;
        this.withText = withText;
        this.exerciseIcon = exerciseIcon;
    }

    /**
     * @return If is going to provide picture to the user or not
     */
    public boolean isWithPicture() {
        return withPicture;
    }


    /**
     * @return If is going to provide audio to the user or not
     */
    public boolean isWithAudio() {
        return withAudio;
    }


    /**
     * @return Is is going to provide the audio to the user or not
     */
    public boolean isWithText() {
        return withText;
    }

    /**
     * @return The id of the resource of icon of the exercise
     */
    public Integer getExerciseIcon() {
        return exerciseIcon;
    }

    /**
     * @return The to string of the exercise type
     */
    @Override
    public String toString() {
        return "ExerciseType{" +
                "withPicture=" + withPicture +
                ", withAudio=" + withAudio +
                ", withText=" + withText +
                '}';
    }
}
