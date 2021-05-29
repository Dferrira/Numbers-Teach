package com.dferreira.numbers_teach.scores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.commons.GenericActivity;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon.ExerciseTypeIcon;
import com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon.ExerciseTypeIconFactory;
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity;

/**
 * Show the list of scores that the user got in a certain exercise
 */
public class ExerciseScoresListActivity extends GenericActivity implements IScoreExerciseActivity {

    /*Key of the language parameter passed to the activity*/
    public final static String LANGUAGE_KEY = "language";

    public final static String EXERCISE_ACTIVITY_KEY = "exercise_activity";

    public final static String EXERCISE_TYPE_KEY = "exercise_type";


    /*Language that was selected by the user to learn*/
    private String languageSelected;

    /*String with name of the activity of the exercise selected*/
    private String exerciseActivitySelected;

    /*If the right picture was show to the user or not*/
    /*If the right audio was play to the user or not*/
    /*If the right text was show to the user or not*/
    private ExerciseType exerciseType;


    /**
     * Get the information of the intent and set it in the local variables of the
     * of the activity
     */
    private void setLocalExerciseVariables() {
        Intent intent = getIntent();
        this.languageSelected = intent.getStringExtra(LANGUAGE_KEY);
        this.exerciseActivitySelected = intent.getStringExtra(EXERCISE_ACTIVITY_KEY);
        this.exerciseType = (ExerciseType) intent.getSerializableExtra(EXERCISE_TYPE_KEY);
    }

    /**
     * Depending which was the exercise selected will show the matching icon
     */
    private void setIconTitle() {
        if (exerciseType == null) {
            return;
        }
        ExerciseTypeIconFactory exerciseTypeIconFactory = new ExerciseTypeIconFactory();
        ExerciseTypeIcon icon = exerciseTypeIconFactory.createIcon(exerciseType);
        Integer iconId = icon.getExerciseIcon();
        setupActionBar(iconId);
    }

    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last known state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setLocalExerciseVariables();

        this.setContentView(R.layout.exercise_scores_list_activity);

        setIconTitle();

    }


    /**
     * Return the language selected by the user know by the activity
     */
    @Override
    public String getLanguagePrefix() {
        return this.languageSelected;
    }


    /**
     * @return The complete name of the activity where were got the results
     */
    @Override
    public String getExerciseActivityName() {
        return this.exerciseActivitySelected;
    }

    /**
     * @return the type of exercise selected by the user to see its previous score
     */
    @Override
    public ExerciseType getExerciseType() {
        return this.exerciseType;
    }


    /**
     * Set the properties of the exercise selected by the user
     *
     * @param exerciseActivitySelected of the activity of the exercise
     * @param exerciseType             the flag to if the exercise was showing (picture,audio,text) or not
     * @param triggerView              View that trigger the event
     */
    @Override
    public void setExerciseProperties(String exerciseActivitySelected, ExerciseType exerciseType, View triggerView) {
        this.exerciseActivitySelected = exerciseActivitySelected;
        this.exerciseType = exerciseType;
    }
}
