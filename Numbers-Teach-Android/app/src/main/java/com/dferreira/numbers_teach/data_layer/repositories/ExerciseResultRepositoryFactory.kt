package com.dferreira.numbers_teach.data_layer.repositories

import android.content.Context
import com.dferreira.numbers_teach.data_layer.database_driver.DataBaseSingleton

class ExerciseResultRepositoryFactory {
    fun createExerciseResultRepository(context: Context): ExerciseResultRepository {
        val database = DataBaseSingleton.getDatabase(context)
        val exerciseResultDao = database.exerciseResultQueries
        return ExerciseResultRepositoryImpl(
            exerciseResultDao
        )
    }
}