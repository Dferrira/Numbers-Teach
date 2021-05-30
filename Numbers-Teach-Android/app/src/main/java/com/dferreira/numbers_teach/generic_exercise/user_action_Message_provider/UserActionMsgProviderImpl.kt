package com.dferreira.numbers_teach.generic_exercise.user_action_Message_provider

import android.content.Intent
import com.dferreira.numbers_teach.generic_exercise.UserActionMsgType
import java.io.Serializable
import java.util.ArrayList


/**
 * Send intents to the broadcast receivers about which were the actions took
 */
class UserActionMsgProviderImpl : UserActionMsgProvider {
    /**
     * List of intents to be used treat by the listeners
     */
    private val intentsQueue: MutableList<Intent>

    /**
     * @param intent Intent that was received
     * @return The message type
     */
    override fun getMsgType(intent: Intent): UserActionMsgType? {
        return intent.getSerializableExtra(TYPE_KEY) as UserActionMsgType?
    }

    /**
     * @param intent Intent that was received
     * @return The the passed in the intent if any
     */
    override fun getTag(intent: Intent): Any? {
        return intent.getSerializableExtra(TAG_KEY)
    }

    /**
     * Indicate to the listener that should replay the last audio
     */
    override fun triggerReplay() {
        synchronized(intentsQueue) {
            val intent =
                Intent(NOTIFICATION)
            intent.putExtra(
                TYPE_KEY,
                UserActionMsgType.REPLAY_AUDIO
            )
            intentsQueue.add(intent)
        }
    }

    /**
     * Used to indicate to the service that the user have show one option
     */
    override fun triggerOptionSelected(tag: Any?) {
        synchronized(intentsQueue) {
            val intent =
                Intent(NOTIFICATION)
            intent.putExtra(
                TAG_KEY,
                tag as Serializable?
            )
            intent.putExtra(
                TYPE_KEY,
                UserActionMsgType.OPTION_SELECT
            )
            intentsQueue.add(intent)
        }
    }

    /**
     * Get a intent from the queue of intents
     *
     * @return The next intent to threat if any
     */
    override fun popIntent(): Intent? {
        synchronized(intentsQueue) {
            return if (intentsQueue.isEmpty()) {
                null
            } else {
                intentsQueue.removeAt(0)
            }
        }
    }

    companion object {
        private const val NOTIFICATION = "com.dferreira.numbers_teach.exercise.user_broadcast"

        /**
         * Key of the element score of the user in the end of the exercise
         */
        private const val TAG_KEY = "tag"
        private const val TYPE_KEY = "Type"
    }

    /**
     * Constructor
     */
    init {
        intentsQueue = ArrayList()
    }
}