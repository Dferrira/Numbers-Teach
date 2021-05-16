package com.dferreira.numbers_teach.exercise_icons.models.exercise_type_description

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType

class ExerciseTypeDescriptionFactory {
    fun createDescription(exerciseType: ExerciseType) = when(exerciseType) {
        ExerciseType.SelectImages -> ExerciseTypeDescription(
            isWithPicture = false,
            isWithAudio = true,
            withText = true
        )
        ExerciseType.Read -> ExerciseTypeDescription(
            isWithPicture = true,
            isWithAudio = false,
            withText = true
        )
        ExerciseType.Listen -> ExerciseTypeDescription(
            isWithPicture = true,
            isWithAudio = true,
            withText = false
        )
    }
}