package com.dferreira.numbers_teach.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.commons.GenericActivity;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity;
import com.dferreira.numbers_teach.languages_dashboard.views.UIHelper;

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
public class GlobalScoresListActivity extends GenericActivity implements IScoreExerciseActivity {

    /*Key of the language parameter passed to the activity*/
    @SuppressWarnings("WeakerAccess")
    public final static String LANGUAGE_KEY = "language";

    /*Language that was selected by the user to learn*/
    private String languageSelected;

    /*String with name of the activity of the exercise selected*/
    private String exerciseActivityName;

    /*If the right picture was show to the user or not*/
    /*If the right audio was play to the user or not*/
    /*If the right text was show to the user or not*/
    private ExerciseType exerciseType;

    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last known state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            this.languageSelected = getIntent().getStringExtra(LANGUAGE_KEY);
        } else {
            this.languageSelected = savedInstanceState.getString(LANGUAGE_KEY);
        }


        this.setContentView(R.layout.global_scores_list_activity);

        this.setupActionBar(R.mipmap.leaderboard);
    }

    /**
     * Called when the activity needs to get the result save for any reason
     *
     * @param savedInstanceState Bundle to get the saved state of the activity
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(LANGUAGE_KEY, languageSelected);
    }

    /**
     * Return the language selected by the user know by the activity
     */
    @Override
    public String getLanguagePrefix() {
        return this.languageSelected;
    }


    /**
     * Start an activity that show a list of scores of a specific exercise
     *
     * @param triggerView View that trigger the event
     */
    private void startExerciseScoresListActivity(View triggerView) {
        Intent intent = new Intent(this, ExerciseScoresListActivity.class);
        intent.putExtra(ExerciseScoresListActivity.LANGUAGE_KEY, languageSelected);
        intent.putExtra(ExerciseScoresListActivity.EXERCISE_ACTIVITY_KEY, exerciseActivityName);
        intent.putExtra(ExerciseScoresListActivity.EXERCISE_TYPE_KEY, exerciseType);

        UIHelper.startNewActivity(this, triggerView, intent);

    }

    /**
     * Set the properties of the exercise selected by the user
     *
     * @param exerciseActivitySelected of the activity of the exercise
     * @param exerciseType             Indicates if the user was seeing the correct (picture,audio, text) when got the result
     * @param triggerView              View that trigger the event
     */
    @Override
    public void setExerciseProperties(String exerciseActivitySelected, ExerciseType exerciseType, View triggerView) {
        this.exerciseActivityName = exerciseActivitySelected;
        this.exerciseType = exerciseType;

        ExerciseScoresListFragment exerciseScoreList = (ExerciseScoresListFragment) getSupportFragmentManager().findFragmentById(R.id.exercise_list_fragment);

        if (exerciseScoreList == null) {
            startExerciseScoresListActivity(triggerView);
        } else {
            exerciseScoreList.updateListOfScores();
        }
    }

    /**
     * @return the name of the activity where the the score was got the score
     */
    @Override
    public String getExerciseActivityName() {
        return this.exerciseActivityName;
    }


    /**
     * @return the type of exercise
     */
    public ExerciseType getExerciseType() {
        return this.exerciseType;
    }


}
