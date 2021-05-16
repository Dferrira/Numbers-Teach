package com.dferreira.numbers_teach.exercise_icons.ui.menu_item_to_exercise_type_converter

import android.view.MenuItem
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType

interface IMenuItemToExerciseTypeConverter {
    fun toExerciseType(menuItem: MenuItem): ExerciseType
}