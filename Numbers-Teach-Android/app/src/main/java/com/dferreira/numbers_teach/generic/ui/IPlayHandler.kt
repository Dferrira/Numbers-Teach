package com.dferreira.numbers_teach.generic.ui

/**
 * Should be implement for those that provide a sequence of data
 * with play and pause
 */
interface IPlayHandler {
    /**
     * Update the state of the play/pause button(s)
     */
    fun updatePlayPauseButtons()
}