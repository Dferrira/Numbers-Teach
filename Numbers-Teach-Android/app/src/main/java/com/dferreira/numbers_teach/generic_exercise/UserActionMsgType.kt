package com.dferreira.numbers_teach.generic_exercise

/**
 * Message send to the service when the user takes one certain action
 */
enum class UserActionMsgType {
    /**
     * When the user wants to re-listen the audio
     */
    REPLAY_AUDIO,

    /**
     * When the user selects one certain option
     */
    OPTION_SELECT
}