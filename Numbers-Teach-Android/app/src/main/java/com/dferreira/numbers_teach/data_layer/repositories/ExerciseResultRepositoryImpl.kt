package com.dferreira.numbers_teach.data_layer.repositories

import android.util.Log
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.helpers.ErrorString
import com.dferreira.numbersteach.datalayer.model.ExerciseResult
import com.dferreira.numbersteach.datalayer.model.ExerciseResultQueries
import kotlinx.datetime.*
import java.lang.Exception

/**
 * Provide all the necessary methods to access to the model exercise result
 */
class ExerciseResultRepositoryImpl(
    private val exerciseResultDao: ExerciseResultQueries
) :
    ExerciseResultRepository {

    /**
     * Save the score of the user in database
     *
     * @param activity     activity where the score was achieved
     * @param language     Language that the user got the score to save
     * @param finalScore   score achieved
     * @param maxScore     maximum score that was possible to achieved
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text when got the result
     */
    override fun saveScore(
        activity: String,
        language: String,
        finalScore: Long,
        maxScore: Long,
        exerciseType: ExerciseType
    ): ExerciseResult? {
        val createdDateString = getCurrentDateAsString()
        val exerciseTypeString = exerciseType.toString()
        val exerciseResult = ExerciseResult(
            0,
            activity,
            language,
            finalScore,
            maxScore,
            createdDateString,
            exerciseTypeString
        )
        return try {
            exerciseResultDao.insert(
                exerciseResult
            )
            exerciseResult
        } catch (e: Exception) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_CREATE_ENTITY, e)
            null
        }
    }

    private fun getCurrentDateAsString(): String {
        val currentMoment: Instant = Clock.System.now()
        val datetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        return datetimeInUtc.toString()
    }

    /**
     * @param language Language to the get the results
     * @return A list of latest the scores saved in grouped by type of exercise
     */
    override fun getGlobalScoresList(language: String): List<ExerciseResult> {
        return try {
            exerciseResultDao
                .getGlobalScoresList(language)
                .executeAsList()
        } catch (e: Exception) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_QUERY_ENTITIES, e)
            emptyList()
        }
    }

    /**
     * @param language     Language which were got the results
     * @param activity     Activity where was got the score
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text) when got the result
     * @return The list of results got in th specified exercise
     */
    override fun getExerciseScoresList(
        language: String,
        activity: String,
        exerciseType: ExerciseType
    ): List<ExerciseResult> {
        return try {
            exerciseResultDao
                .getExerciseScoresList(language, activity, exerciseType.toString())
                .executeAsList()
        } catch (e: Exception) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_QUERY_ENTITIES, e)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "ResultRepository"
    }
}