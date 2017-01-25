package com.dferreira.numbers_teach.activities_list;


import android.os.Bundle;
import android.view.Menu;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.commons.GenericActivity;
import com.dferreira.numbers_teach.exercise_icons.views.ExerciseIconsHelper;
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity;
import com.dferreira.numbers_teach.languages_dashboard.views.LanguageDashboardHelper;
import com.dferreira.numbers_teach.languages_dashboard.views.LanguagesDashboardFragment;

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
public class ActivitiesListActivity extends GenericActivity implements ILanguageActivity {

    /*Language that was selected by the user to learn*/
    private String languageSelected;

    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last know state of the bundle of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            this.languageSelected = getIntent().getStringExtra(LanguagesDashboardFragment.LANGUAGE_KEY);
        } else {
            this.languageSelected = savedInstanceState.getString(LanguagesDashboardFragment.LANGUAGE_KEY);
        }

        this.setContentView(R.layout.activities_list_activity);

        Integer resFlagId = LanguageDashboardHelper.getLanguageFlagResId(this, this.languageSelected);
        setupActionBar(resFlagId);
    }


    /**
     * Return the language selected by the user know by the activity
     */
    @Override
    public String getLanguagePrefix() {
        return this.languageSelected;
    }


    /**
     * Inflates the existing menu
     *
     * @param menu Menu to be inflated
     * @return false something got wrong
     * true everything got alright
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(ExerciseIconsHelper.getExerciseIconsMenu(), menu);
        return true;
    }

    /**
     * Called when the activity needs to get the result save for any reason
     *
     * @param savedInstanceState Bundle to get the saved state of the activity
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(LanguagesDashboardFragment.LANGUAGE_KEY, languageSelected);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Called when is to restore one instance
     *
     * @param savedInstanceState Bundle where the previous state of the activity was saved
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.languageSelected = savedInstanceState.getString(LanguagesDashboardFragment.LANGUAGE_KEY);
    }
}
