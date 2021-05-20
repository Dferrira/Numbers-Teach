package com.dferreira.numbers_teach.delegators

import android.content.Context
import android.media.MediaPlayer
import java.util.concurrent.Semaphore

class AudioDelegatorFactory {

    fun createAudioDelegator(context: Context): IAudioDelegator {
        val mediaPlayer = MediaPlayer()
        val semaphore = Semaphore(1)
        return AudioDelegator(
            context,
            mediaPlayer,
            semaphore
        )
    }
}