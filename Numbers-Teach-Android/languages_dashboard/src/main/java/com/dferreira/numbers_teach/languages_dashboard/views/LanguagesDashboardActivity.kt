package com.dferreira.numbers_teach.languages_dashboard.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.dferreira.numbers_teach.commons.GenericActivity
import com.dferreira.numbers_teach.languages_dashboard.R

/**
 * Activity of main languages dashboard
 */
class LanguagesDashboardActivity : GenericActivity() {
    /**
     * Method on create of the activity lifecycle
     *
     * @param savedInstanceState The saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }
}