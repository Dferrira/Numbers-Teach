package com.dferreira.numbers_teach.exercise_icons.ui.menu_item_to_exercise_type_converter

import android.view.MenuItem
import com.dferreira.numbers_teach.exercise_icons.R
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType

class MenuItemToExerciseTypeConverter : IMenuItemToExerciseTypeConverter {

    override fun toExerciseType(menuItem: MenuItem): ExerciseType {
        val menuItemId: Int = menuItem.itemId
        return toExerciseType(menuItemId)
    }

    private fun toExerciseType(menuItemId: Int): ExerciseType {
        return when (menuItemId) {
            R.id.listening_and_read_exercise -> {
                ExerciseType.SelectImages
            }
            R.id.select_picture_listening_exercise -> {
                ExerciseType.Listen
            }
            R.id.select_picture_read_exercise -> {
                ExerciseType.Read
            }
            else -> throw NotImplementedError()
        }
    }
}