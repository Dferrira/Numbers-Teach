package com.dferreira.numbers_teach.generic_exercise

import android.view.View

/**
 * Interface of the choice that was selected
 */
interface ISelectedChoice {
    /**
     * Set the background of the droppable area to notice that something has entered there
     */
    fun notifyEnteredInDroppableArea()

    /**
     * Indicate that the dragged element have leave the droppable area
     */
    fun notifyLeaveDroppableArea()

    /**
     * Is going to verify if the view that was selected matches the expected  image
     *
     * @param selectedView to check if matches
     */
    fun notifyViewSelected(selectedView: View?)

    /**
     * When the user have made the correct choice is going to get some feedback
     */
    fun showCorrectChoice()

    /**
     * When the user have made the wrong choice is going to get some feedback
     */
    fun showWrongChoice()

    /**
     * Ideal to reset any view that needs to be clean between passages
     */
    fun resetViews()
}