package com.dferreira.numbers_teach.models;

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Model in database that allows to save which was the score of the user
 * in each exercise
 */
@SuppressWarnings("WeakerAccess")
@DatabaseTable(tableName = ExerciseResult.TABLE_NAME)
public class ExerciseResult {
    public static final String TABLE_NAME = "exercise_result";

    /**
     * Activity which the result were got
     */
    public static final String ACTIVITY_COLUMN = "activity";

    /**
     * Language where the result was obtain
     */
    public static final String LANGUAGE_COLUMN = "language";

    /**
     * Final score got by the user
     */
    public static final String FINAL_SCORE_COLUMN = "final_score";

    /**
     * Max score that would be possible to get in the exercise
     */
    public static final String MAX_SCORE_COLUMN = "max_score";

    /**
     * Date when the row was created
     */
    public static final String CREATED_DATE_COLUMN = "created_date";

    /**
     * Indicate if the user go the score watching pictures
     */
    public static final String EXERCISE_TYPE_COLUMN = "exercise_type";


    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = ACTIVITY_COLUMN)
    private String activity;

    @DatabaseField(columnName = LANGUAGE_COLUMN)
    private String language;

    @DatabaseField(columnName = FINAL_SCORE_COLUMN)
    private int finalScore;

    @DatabaseField(columnName = MAX_SCORE_COLUMN)
    private int maxScore;

    @DatabaseField(columnName = CREATED_DATE_COLUMN)
    private Date createdDate;

    @DatabaseField(columnName = EXERCISE_TYPE_COLUMN)
    private ExerciseType exerciseType;


    /**
     * @return The identifier of the entity in database
     */
    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    /**
     * Sets the identifier of the entity in database
     *
     * @param id The identifier itself
     */
    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The name of the activity where the result was got
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity Set the name of the activity where the result was got
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return The prefix of the language played to the such score
     */
    @SuppressWarnings("unused")
    public String getLanguage() {
        return language;
    }

    /**
     * Set the language used to get such score
     *
     * @param language language that was played
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return Get the final score that was got by the user
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Set the final score got by the user
     *
     * @param finalScore Final score of the user
     */
    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * @return The maximum score that were possible to get in the specified exercise
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * @param maxScore Set the maximum score possible to get in the exercise
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Get the date when the entity was created
     *
     * @return The date when the entity was created
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the date when the entity was created
     *
     * @param createdDate date when the entity was created
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
}
