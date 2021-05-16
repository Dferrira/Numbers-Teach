package com.dferreira.numbers_teach.exercise_icons.models.exercise_type_description

/**
 * Structure that indicates which kind of exercise it is
 */
data class ExerciseTypeDescription(

    /**
     * @return If is going to provide picture to the user or not
     */
    val isWithPicture: Boolean,

    /**
     * @return If is going to provide audio to the user or not
     */
    val isWithAudio: Boolean,

    /**
     * @return Is is going to provide the audio to the user or not
     */
    val withText: Boolean
    )