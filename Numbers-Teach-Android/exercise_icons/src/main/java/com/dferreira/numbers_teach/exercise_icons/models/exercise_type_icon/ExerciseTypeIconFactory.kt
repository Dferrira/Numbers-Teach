package com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType

class ExerciseTypeIconFactory {
    fun createIcon(exerciseType: ExerciseType): ExerciseTypeIcon = when(exerciseType) {
        ExerciseType.SelectImages -> SelectImagesIcon
        ExerciseType.Read -> ReadIcon
        ExerciseType.Listen -> ListenIcon
    }
}