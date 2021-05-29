package com.dferreira.numbers_teach.ui_models

import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.lesson.LessonActivity
import com.dferreira.numbers_teach.preferences.PreferencesActivity
import com.dferreira.numbers_teach.scores.GlobalScoresListActivity
import com.dferreira.numbers_teach.select_images_exercise.SelectImagesExerciseActivity

public class ActivityItemFactory {
    fun createActivityItemList(): List<ActivityItem> {
        val lesson = createLesson()
        val exercises = createExercises()
        val scores = createResults()
        val preferences = createPreferences()
        return listOf(
            lesson,
            exercises,
            scores,
            preferences
        )
    }

    private fun createLesson(): ActivityItem {
        return ActivityItem(
            R.mipmap.classroom,
            R.string.lesson,
            LessonActivity::class.java,
            false
        )
    }

    private fun createExercises(): ActivityItem {
        return ActivityItem(
            R.mipmap.exercises,
            R.string.exercises,
            SelectImagesExerciseActivity::class.java,
            true
        )
    }

    private fun createResults(): ActivityItem {
        return ActivityItem(
            R.mipmap.leaderboard,
            R.string.results,
            GlobalScoresListActivity::class.java,
            false
        )
    }

    private fun createPreferences(): ActivityItem {
        return ActivityItem(
            R.mipmap.ic_settings_black_48dp,
            R.string.preferences,
            PreferencesActivity::class.java,
            false
        )
    }
}