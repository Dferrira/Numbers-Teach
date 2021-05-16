package com.dferreira.numbers_teach

import android.app.Application
import com.dferreira.numbers_teach.activities_list.ActivitiesListActivity
import com.dferreira.numbers_teach.commons.IGenericStudySet
import com.dferreira.numbers_teach.languages_dashboard.views.LanguagesDashboardFragment
import com.dferreira.numbers_teach.numbers.models.StudySetFactory

/**
 * Forces some data before start the activities
 */
class NumberTeachApplication : Application() {
    /**
     * Indicates to the dashboard module which activities should be launch when the user picks
     * one language
     */
    private fun initializeDashboard() {
        LanguagesDashboardFragment.teachingActivity = ActivitiesListActivity::class.java
    }

    /**
     * Initialize the study set that the user is going to see
     */
    private fun initializeStudySet() {
        val studySetFactory = StudySetFactory()
        studySetInstance = studySetFactory.createStudySet(this.baseContext)
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     */
    override fun onCreate() {
        super.onCreate()
        initializeStudySet()
        initializeDashboard()
    }

    companion object {
        /**
         * @return The study set that the application was compiled for
         */
        /**
         * Study set of the resources repository
         */
        var studySetInstance: IGenericStudySet? = null
            private set
    }
}