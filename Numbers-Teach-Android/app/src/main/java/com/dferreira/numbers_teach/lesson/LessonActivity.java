package com.dferreira.numbers_teach.lesson;

import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity;

/**
 * Show a lesson_fragment representing the the numbers to teach
 */
public class LessonActivity extends FragmentActivity implements ILanguageActivity {

    /*Key of the language parameter passed to the activity*/
    @SuppressWarnings("WeakerAccess")
    public final static String LANGUAGE_KEY = "language";


    /*Language that was selected by the user to learn*/
    private String languageSelected;

    /**
     * set the content view
     *
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.languageSelected = getIntent().getStringExtra(LANGUAGE_KEY);

        this.setContentView(R.layout.lesson_activity);
    }


    /**
     * Method that will be called when the user presses back
     * Will inform the service that should terminate itself in order to free resources
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LessonService.disableToRestoreState();
    }

    /**
     * Return the language selected by the user know by the activity
     */
    @Override
    public String getLanguagePrefix() {
        return this.languageSelected;
    }
}
