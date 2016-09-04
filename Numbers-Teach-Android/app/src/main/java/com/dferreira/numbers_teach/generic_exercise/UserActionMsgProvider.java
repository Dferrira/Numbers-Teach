package com.dferreira.numbers_teach.generic_exercise;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Send intents to the broadcast receivers about which were the actions took
 */
public class UserActionMsgProvider {
    private static final String NOTIFICATION = "com.dferreira.numbers_teach.exercise.user_broadcast";

    /**
     * Key of the element score of the user in the end of the exercise
     */
    private static final String TAG_KEY = "tag";

    private static final String TYPE_KEY = "Type";

    /**
     * List of intents to be used treat by the listeners
     */
    private final List<Intent> intentsQueue;

    private static UserActionMsgProvider instance;


    /**
     * Constructor
     */
    private UserActionMsgProvider() {
        this.intentsQueue = new ArrayList<>();
    }

    /**
     * Object get via factory
     *
     * @return An instance of the UserActionMsgProvider
     */
    public static UserActionMsgProvider getInstance() {
        if (instance == null) {
            instance = new UserActionMsgProvider();
        }
        return instance;
    }

    /**
     * Indicate to the listener that should replay the last audio
     */
    public void triggerReplay() {
        synchronized (intentsQueue) {
            Intent intent = new Intent(NOTIFICATION);
            intent.putExtra(TYPE_KEY, UserActionMsgType.REPLAY_AUDIO);
            this.intentsQueue.add(intent);
        }
    }

    /**
     * Used to indicate to the service that the user have show one option
     */
    public void triggerOptionSelected(Object tag) {
        synchronized (this.intentsQueue) {
            Intent intent = new Intent(NOTIFICATION);
            intent.putExtra(TAG_KEY, (Serializable) tag);
            intent.putExtra(TYPE_KEY, UserActionMsgType.OPTION_SELECT);
            intentsQueue.add(intent);
        }
    }

    /**
     * Get a intent from the queue of intents
     *
     * @return The next intent to threat if any
     */
    public Intent popIntent() {
        synchronized (this.intentsQueue) {
            if (this.intentsQueue.isEmpty()) {
                return null;
            } else {
                return this.intentsQueue.remove(0);
            }
        }
    }

    /**
     * @param intent Intent that was received
     * @return The message type
     */
    public static UserActionMsgType getMsgType(Intent intent) {
        return (UserActionMsgType) intent.getSerializableExtra(TYPE_KEY);
    }

    /**
     * @param intent Intent that was received
     * @return The the passed in the intent if any
     */
    public static Object getTag(Intent intent) {
        return intent.getSerializableExtra(TAG_KEY);
    }
}
