package com.dferreira.numbers_teach.data_layer.model

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbersteach.datalayer.model.ExerciseResult

fun ExerciseResult.getExerciseType(): ExerciseType {
    val exerciseTypeString = this.exercise_type
    return ExerciseType.valueOf(exerciseTypeString)
}