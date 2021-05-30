package com.dferreira.numbers_teach.generic_exercise.user_action_Message_provider

import android.content.Intent
import com.dferreira.numbers_teach.generic_exercise.UserActionMsgType

interface UserActionMsgProvider {

    /**
     * @param intent Intent that was received
     * @return The message type
     */
    fun getMsgType(intent: Intent): UserActionMsgType?

    /**
     * @param intent Intent that was received
     * @return The the passed in the intent if any
     */
    fun getTag(intent: Intent): Any?

    /**
     * Indicate to the listener that should replay the last audio
     */
    fun triggerReplay()

    /**
     * Used to indicate to the service that the user have show one option
     */
    fun triggerOptionSelected(tag: Any?)

    /**
     * Get a intent from the queue of intents
     *
     * @return The next intent to threat if any
     */
    fun popIntent(): Intent?
}
