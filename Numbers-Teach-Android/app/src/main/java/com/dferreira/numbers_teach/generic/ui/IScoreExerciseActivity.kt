package com.dferreira.numbers_teach.generic.ui

import android.view.View
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType

interface IScoreExerciseActivity: IExerciseActivity {
    /**
     * @return the name of the activity where the the score was got the score
     */
    fun exerciseActivityName(): String?

    /**
     * Set the properties of the exercise selected by the user
     *
     * @param exerciseActivitySelected Of the activity of the exercise
     * @param exerciseType             Indicates if the user was seeing the correct (picture,audio, text) when got the result
     * @param triggerView              View that trigger the event
     */
    fun setExerciseProperties(
        exerciseActivitySelected: String?,
        exerciseType: ExerciseType?,
        triggerView: View
    )
}