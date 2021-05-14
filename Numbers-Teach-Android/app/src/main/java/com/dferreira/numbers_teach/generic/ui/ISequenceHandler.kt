package com.dferreira.numbers_teach.generic.ui

/**
 * Defines the interface to
 * take care of the transitions from to slides
 */
interface ISequenceHandler {
    /**
     * Control forward button behavior
     */
    fun forward()

    /**
     * Control previous button behavior
     */
    fun previous()
}