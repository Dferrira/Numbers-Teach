package com.dferreira.numbers_teach.languages_dashboard.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dferreira.numbers_teach.commons.GenericActivity;
import com.dferreira.numbers_teach.languages_dashboard.R;

/**
 * Activity of main languages dashboard
 */
@SuppressLint("Registered")
public class LanguagesDashboardActivity extends GenericActivity {

    /**
     * Method on create of the activity lifecycle
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
