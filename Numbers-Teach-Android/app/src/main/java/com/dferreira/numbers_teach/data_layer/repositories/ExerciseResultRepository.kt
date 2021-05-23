package com.dferreira.numbers_teach.data_layer.repositories

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbersteach.datalayer.model.ExerciseResult

interface ExerciseResultRepository {
    fun saveScore(
        activity: String,
        language: String,
        finalScore: Long,
        maxScore: Long,
        exerciseType: ExerciseType
    ): ExerciseResult?

    fun getGlobalScoresList(language: String): List<ExerciseResult>

    fun getExerciseScoresList(
        language: String,
        activity: String,
        exerciseType: ExerciseType
    ): List<ExerciseResult>
}