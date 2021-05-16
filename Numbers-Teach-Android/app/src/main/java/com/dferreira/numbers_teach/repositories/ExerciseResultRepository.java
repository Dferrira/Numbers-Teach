package com.dferreira.numbers_teach.repositories;

import android.content.Context;
import android.util.Log;

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.helpers.ErrorString;
import com.dferreira.numbers_teach.helpers.NumbersTeachDatabaseHelper;
import com.dferreira.numbers_teach.models.ExerciseResult;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.Date;
import java.util.List;

/**
 * Provide all the necessary methods to access to the model exercise result
 */
public class ExerciseResultRepository {

    private static final String TAG = "ResultRepository";
    private final NumbersTeachDatabaseHelper dbHelper;

    /**
     * Constructor to the repository to access the entities exerciseResult
     *
     * @param context Context where this method is call
     */
    public ExerciseResultRepository(Context context) {
        this.dbHelper = NumbersTeachDatabaseHelper.getInstance(context);
    }

    /**
     * Save the score of the user in database
     *
     * @param activity     activity where the score was achieved
     * @param language     Language that the user got the score to save
     * @param finalScore   score achieved
     * @param maxScore     maximum score that was possible to achieved
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text when got the result
     */
    public void saveScore(String activity, String language, int finalScore, @SuppressWarnings("SameParameterValue") int maxScore, ExerciseType exerciseType) {
        Date currentDate = new Date();

        ExerciseResult exerciseResult = new ExerciseResult();

        /*Set the values of the Pojo*/
        exerciseResult.setActivity(activity);
        exerciseResult.setLanguage(language);
        exerciseResult.setFinalScore(finalScore);
        exerciseResult.setMaxScore(maxScore);
        exerciseResult.setCreatedDate(currentDate);
        exerciseResult.setExerciseType(exerciseType);
        try {
            dbHelper.getExerciseResultDao().create(exerciseResult);
        } catch (Exception e) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_CREATE_ENTITY, e);
        }
    }

    /**
     * @param language Language to the get the results
     * @return A list of latest the scores saved in grouped by type of exercise
     */
    public List<ExerciseResult> getGlobalScoresList(String language) {
        try {
            QueryBuilder<ExerciseResult, Long> queryBuilder = dbHelper.getExerciseResultDao().queryBuilder();
            Where<ExerciseResult, Long> where = queryBuilder.where();
            where.eq(ExerciseResult.LANGUAGE_COLUMN, language);
            queryBuilder.groupBy(ExerciseResult.ACTIVITY_COLUMN);
            queryBuilder.groupBy(ExerciseResult.EXERCISE_TYPE_COLUMN);
            queryBuilder.orderBy(ExerciseResult.CREATED_DATE_COLUMN, false);
            return dbHelper.getExerciseResultDao().query(where.prepare());
        } catch (Exception e) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_QUERY_ENTITIES, e);
            return null;
        }
    }

    /**
     * @param language     Language which were got the results
     * @param activity     Activity where was got the score
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text) when got the result
     * @return The list of results got in th specified exercise
     */
    public List<ExerciseResult> getExerciseScoresList(String language, String activity, ExerciseType exerciseType) {
        try {
            QueryBuilder<ExerciseResult, Long> queryBuilder = dbHelper.getExerciseResultDao().queryBuilder();
            Where<ExerciseResult, Long> where = queryBuilder.where();
            //noinspection unchecked
            where.and(
                    where.eq(ExerciseResult.LANGUAGE_COLUMN, language),
                    where.eq(ExerciseResult.ACTIVITY_COLUMN, activity),
                    where.eq(ExerciseResult.EXERCISE_TYPE_COLUMN, exerciseType)
            );
            queryBuilder.orderBy(ExerciseResult.CREATED_DATE_COLUMN, false);
            return dbHelper.getExerciseResultDao().query(where.prepare());
        } catch (Exception e) {
            Log.d(TAG, ErrorString.IMPOSSIBLE_QUERY_ENTITIES, e);
            return null;
        }
    }
}
