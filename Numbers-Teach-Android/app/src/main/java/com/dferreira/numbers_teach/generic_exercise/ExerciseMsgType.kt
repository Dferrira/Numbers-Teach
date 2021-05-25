package com.dferreira.numbers_teach.generic_exercise

/**
 * Enum to specify switch type of message is send to to the broadcast receiver
 * In the case o exercises
 */
enum class ExerciseMsgType {
    /**
     * Update the list of choices of that the user has
     */
    UPDATE_LIST_OF_CHOICES,

    /**
     * Show the user if the choice that was picked was correct or incorrect
     */
    SHOW_RESULT_OF_CHOICE,

    /**
     * Show the user which was its final score
     */
    SHOW_SCORE,

    /**
     * Go back to the previous screen
     */
    FINISHING_ACTIVITY
}