package com.dferreira.numbers_teach.generic.gestures;


/**
 * Classes that want to handle the gestures of the user should implement this interface
 */
public interface IGesturesHandler {

    /**
     * When the user tries drag to left the element
     */
    void dragToLeft();

    /**
     * When the user tries drag to right the element
     */
    void dragToRight();

    /**
     * When the user tries drag up the element
     */
    void dragUp();

    /**
     * When the user tries drag down the element
     */
    void dragDown();

    /**
     * When the user taps once the element
     */
    void singleTap();

    /**
     * When the user taps twice the element
     */
    void doubleTap();
}

