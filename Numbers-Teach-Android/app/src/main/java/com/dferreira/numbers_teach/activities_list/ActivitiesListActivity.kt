package com.dferreira.numbers_teach.activities_list

import android.os.Bundle
import android.view.Menu
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.commons.GenericActivity
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity
import com.dferreira.numbers_teach.languages_dashboard.views.LanguageDashboardHelper
import com.dferreira.numbers_teach.languages_dashboard.views.LanguagesDashboardFragment

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
class ActivitiesListActivity : GenericActivity(), ILanguageActivity {
    /*Language that was selected by the user to learn*/
    private lateinit var languageSelected: String

    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last know state of the bundle of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        languageSelected = getLanguageSelected(savedInstanceState)
        this.setContentView(R.layout.activities_list_activity)
        val resFlagId = LanguageDashboardHelper.getLanguageFlagResId(this, languageSelected)
        setupActionBar(resFlagId)
    }

    private fun getLanguageSelected(savedInstanceState: Bundle?): String {
        return if (savedInstanceState == null) {
            intent.getStringExtra(LanguagesDashboardFragment.LANGUAGE_KEY)!!
        } else {
            savedInstanceState.getString(LanguagesDashboardFragment.LANGUAGE_KEY)!!
        }
    }

    /**
     * Return the language selected by the user know by the activity
     */
    override val languagePrefix: String
        get() = languageSelected!!

    /**
     * Inflates the existing menu
     *
     * @param menu Menu to be inflated
     * @return false something got wrong
     * true everything got alright
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(getExerciseIconsMenu(), menu)
        return true
    }

    private fun getExerciseIconsMenu(): Int {
        return R.menu.exercises_menu
    }

    /**
     * Called when the activity needs to get the result save for any reason
     *
     * @param savedInstanceState Bundle to get the saved state of the activity
     */
    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString(LanguagesDashboardFragment.LANGUAGE_KEY, languageSelected)
        super.onSaveInstanceState(savedInstanceState)
    }

    /**
     * Called when is to restore one instance
     *
     * @param savedInstanceState Bundle where the previous state of the activity was saved
     */
    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        languageSelected = savedInstanceState.getString(LanguagesDashboardFragment.LANGUAGE_KEY)!!
    }
}