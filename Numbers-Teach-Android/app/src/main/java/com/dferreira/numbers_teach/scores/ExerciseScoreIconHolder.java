package com.dferreira.numbers_teach.scores;

import android.widget.ImageView;
import android.widget.TextView;


/**
 * Used to no search several times in the item of the list view
 * o improve the performance of UI render
 */
@SuppressWarnings("WeakerAccess")
public class ExerciseScoreIconHolder {

    /**
     * Image view to show the icon of the exercise
     */
    public ImageView icon;

    /**
     * TextView where is going to be show the date when the user got the score
     */
    public TextView date;

    /**
     * TextView where is going to be show the final score of the user
     */
    public TextView score;

}