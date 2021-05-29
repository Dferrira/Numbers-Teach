package com.dferreira.numbers_teach.generic.gestures

/**
 * Classes that want to handle the gestures of the user should implement this interface
 */
interface IGesturesHandler {
    /**
     * When the user tries drag to left the element
     */
    fun dragToLeft()

    /**
     * When the user tries drag to right the element
     */
    fun dragToRight()

    /**
     * When the user tries drag up the element
     */
    fun dragUp()

    /**
     * When the user tries drag down the element
     */
    fun dragDown()

    /**
     * When the user taps once the element
     */
    fun singleTap()

    /**
     * When the user taps twice the element
     */
    fun doubleTap()
}