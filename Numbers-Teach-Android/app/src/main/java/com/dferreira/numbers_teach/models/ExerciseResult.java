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
    public static final String WITH_PICTURE_COLUMN = "with_picture";


    /**
     * Indicate if the user go the score listening audio
     */
    public static final String WITH_AUDIO_COLUMN = "with_audio";


    /**
     * Indicate if the user go the score reading text
     */
    public static final String WITH_TEXT_COLUMN = "with_text";

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

    @DatabaseField(columnName = WITH_PICTURE_COLUMN)
    private boolean withPicture;

    @DatabaseField(columnName = WITH_AUDIO_COLUMN)
    private boolean withAudio;

    @DatabaseField(columnName = WITH_TEXT_COLUMN)
    private boolean withText;

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

    /**
     * @return Indicates if the user was seeing the correct picture when got the result
     */
    public boolean isWithPicture() {
        return withPicture;
    }

    /**
     * Set if the user was seeing the correct picture when got the result
     *
     * @param withPicture the value of the flag
     */
    public void setWithPicture(boolean withPicture) {
        this.withPicture = withPicture;
    }

    /**
     * @return Indicates if the user was listening the correct audio when got the result
     */
    public boolean isWithAudio() {
        return withAudio;
    }

    /**
     * Set the flag that indicates if the audio was produced when the user got the result
     *
     * @param withAudio The result of the flag itself
     */
    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }

    /**
     * @return Indicates if was show to the user the corresponding text
     */
    public boolean isWithText() {
        return withText;
    }

    /**
     * Set the flag that indicates if was present to the user the right text
     *
     * @param withText Set the flag itself
     */
    public void setWithText(boolean withText) {
        this.withText = withText;
    }

    /**
     * @return Get the the exercise type associated with this exercise result
     */
    @SuppressWarnings("UnnecessaryLocalVariable")
    public ExerciseType getExerciseType() {
        ExerciseType exerciseType = new ExerciseType(isWithPicture(), isWithAudio(), isWithText(), null);
        return exerciseType;
    }
}
