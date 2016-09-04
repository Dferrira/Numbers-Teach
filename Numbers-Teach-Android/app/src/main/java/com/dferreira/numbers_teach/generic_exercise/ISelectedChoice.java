package com.dferreira.numbers_teach.generic_exercise;

import android.view.View;

/**
 * Interface of the choice that was selected
 */
public interface ISelectedChoice {

    /**
     * Set the background of the droppable area to notice that something has entered there
     */
    void setEnteredInDroppableArea();

    /**
     * Indicate that the dragged element have leaved the droppable area
     */
    void setLeaveDroppableArea();

    /**
     * Is going to verify if the view that was selected matches the expected  image
     *
     * @param selectedView to check if matches
     */
    void triggerViewSelected(View selectedView);

    /**
     * When the user have made the correct choice is going to get some feedback
     */
    void showCorrectChoice();

    /**
     * When the user have made the wrong choice is going to get some feedback
     */
    void showWrongChoice();

    /**
     * Ideal to reset any view that needs to be clean between passages
     */
    void resetViews();
}
