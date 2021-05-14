package com.dferreira.numbers_teach.scores

import android.widget.ImageView
import android.widget.TextView

data class ExerciseScoreIconHolder (

    /**
     * Image view to show the icon of the exercise
     */
    val icon: ImageView?,

    /**
     * TextView where is going to be show the date when the user got the score
     */
    var date: TextView,

    /**
     * TextView where is going to be show the final score of the user
     */
    var score: TextView?

    )
