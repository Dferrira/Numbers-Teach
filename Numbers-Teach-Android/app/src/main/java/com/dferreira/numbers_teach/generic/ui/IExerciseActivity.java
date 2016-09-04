package com.dferreira.numbers_teach.generic.ui;

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;

/**
 * Interface that should be implemented by the activities in order to provide the information
 * to the its fragments which was the language selected by the user
 */
public interface IExerciseActivity extends ILanguageActivity {

    /**
     * @return the type of exercise
     */
    ExerciseType getExerciseType();
}
