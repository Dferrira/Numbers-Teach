package com.dferreira.numbers_teach.lesson

import java.io.Serializable

/**
 * Enum to specify switch type of message is send to to the broadcast receiver
 */
enum class LessonBroadcastMsgType : Serializable {
    UPDATE_SLIDE_VIEW,
    FINISHING_ACTIVITY,
    CHANGE_PLAYING_STATE
}