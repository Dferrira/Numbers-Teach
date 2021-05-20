package com.dferreira.numbers_teach.delegators

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener


interface IAudioDelegator : MediaPlayer.OnPreparedListener, OnCompletionListener {
    fun prepareToPlay()

    fun playPath(filePath: String)

    fun replayLastAsset()

    fun stopAudio()

    fun waitCompleteAudio()

    fun isNotPreparedToPlay(): Boolean

    fun dispose()
}