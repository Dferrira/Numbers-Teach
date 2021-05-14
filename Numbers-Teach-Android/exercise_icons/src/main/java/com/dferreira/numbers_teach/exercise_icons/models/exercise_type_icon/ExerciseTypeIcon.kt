package com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon

import com.dferreira.numbers_teach.exercise_icons.R

sealed class ExerciseTypeIcon(
    /**
     * @return The id of the resource of icon of the exercise
     */
    val exerciseIcon: Int
)

object SelectImagesIcon: ExerciseTypeIcon(R.mipmap.ic_wallpaper_black_48dp)

object ReadIcon : ExerciseTypeIcon(R.mipmap.read)

object ListenIcon : ExerciseTypeIcon(R.mipmap.ic_hearing_black_48dp)

