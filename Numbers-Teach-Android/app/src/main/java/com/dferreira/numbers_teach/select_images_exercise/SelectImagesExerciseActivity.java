package com.dferreira.numbers_teach.select_images_exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity;

import java.io.Serializable;

/**
 * Activity to pick the right image giving the sound and description
 */
public class SelectImagesExerciseActivity extends FragmentActivity implements IExerciseActivity {
    /*Key of the language parameter passed to the activity*/
    private final static String LANGUAGE_KEY = "language";
    private final static String EXERCISE_TYPE = "exercise_type";


    /*Language that was selected by the user to learn*/
    private String languageSelected;

    /*If should play audio or not*/
    /*If should show text or not*/
    private ExerciseType exerciseType;

    /**
     * Start the activity activity to select the correct picture
     *
     * @param context      Context where the method is called
     * @param language     The language that was selected
     * @param exerciseType Type of exercise with different flags associated
     */
    public static void startSelectImagesExerciseActivity(Context context, String language, ExerciseType exerciseType) {
        Intent intent = new Intent(context, SelectImagesExerciseActivity.class);
        intent.putExtra(SelectImagesExerciseActivity.LANGUAGE_KEY, language);
        intent.putExtra(SelectImagesExerciseActivity.EXERCISE_TYPE, (Serializable) exerciseType);
        context.startActivity(intent);
    }

    /**
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.languageSelected = getIntent().getStringExtra(LANGUAGE_KEY);
        this.exerciseType = (ExerciseType) (getIntent().getSerializableExtra(EXERCISE_TYPE));

        this.setContentView(R.layout.select_images_exercise_activity);
    }

    /**
     * Method that will be called when the user presses back
     * Will inform the service that should terminate itself in order to free resources
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SelectImagesExerciseService.disableToRestoreState();
    }

    /**
     * Return the language selected by the user know by the activity
     */
    @Override
    public String getLanguagePrefix() {
        return this.languageSelected;
    }

    /**
     * @return The reference to the type of exercise
     */
    @Override
    public ExerciseType getExerciseType() {
        return this.exerciseType;
    }

}
