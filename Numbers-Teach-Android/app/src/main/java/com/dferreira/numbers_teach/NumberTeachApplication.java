package com.dferreira.numbers_teach;

import android.app.Application;

import com.dferreira.numbers_teach.activities_list.ActivitiesListActivity;
import com.dferreira.numbers_teach.commons.GenericStudySet;
import com.dferreira.numbers_teach.languages_dashboard.views.LanguagesDashboardFragment;
import com.dferreira.numbers_teach.numbers.models.StudySet;

/**
 * Forces some data before start the activities
 */
public class NumberTeachApplication extends Application {

    /**
     * Study set of the resources repository
     */
    private static GenericStudySet studySet;

    /**
     * @return The study set that the application was compiled for
     */
    public static GenericStudySet getStudySetInstance() {
        return studySet;
    }

    /**
     * Indicates to the dashboard module which activities should be launch when the user picks
     * one language
     */
    private void initializeDashboard() {
        LanguagesDashboardFragment.teachingActivity = ActivitiesListActivity.class;
    }

    /**
     * Initialize the study set that the user is going to see
     */
    private void initializeStudySet() {
        studySet = new StudySet();
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initializeStudySet();
        initializeDashboard();
    }
}
