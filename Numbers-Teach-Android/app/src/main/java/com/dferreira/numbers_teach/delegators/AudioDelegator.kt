package com.dferreira.numbers_teach.delegators

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

/**
 * Responsible for play the audio
 */
class AudioDelegator(
    private val context: Context,
    private val mediaPlayer: MediaPlayer,
    private val semaphore: Semaphore
) :
    IAudioDelegator {
    private val TAG = "AudioDelegator"

    /**
     * Number of seconds that will wait until thin that was not possible to
     * play an audio
     */
    private val AUDIO_TIMEOUT = 5
    private var preparedToPlay = false

    /**
     * Allocates all the resources required to play the given medias
     */
    override fun prepareToPlay() {
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        preparedToPlay = true
    }

    /**
     * Reproduce an audio file
     *
     * @param filePath File path of the file to play
     * true    -> everything was fine
     */
    override fun playPath(filePath: String) {
        try {
            mediaPlayer.reset()
            val assetDescriptor = getFileDescriptor(filePath)
            mediaPlayer.setDataSource(
                assetDescriptor.fileDescriptor,
                assetDescriptor.startOffset,
                assetDescriptor.length
            )
            mediaPlayer.prepareAsync()
            semaphore.tryAcquire(AUDIO_TIMEOUT.toLong(), TimeUnit.SECONDS)
        } catch (e: Exception) {
            Log.e(TAG, "playPath", e)
        }
    }

    /**
     * @param filePath File path of the file to open
     * @return The assetFileDescriptor of the file in the specified path
     * @throws IOException If was not possible open the specified file
     */
    @Throws(IOException::class)
    private fun getFileDescriptor(filePath: String): AssetFileDescriptor {
        val assetManager = context.assets
        return assetManager.openFd(filePath)
    }

    /**
     * Will re-play the las asset
     */
    override fun replayLastAsset() {
        mediaPlayer.seekTo(0)
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    /**
     * Will stop the reproduction of an audio
     */
    override fun stopAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(mediaPlayer.duration)
        }
    }

    /**
     * Forces the thread where the method is called to wait until the file that specified to end
     */
    override fun waitCompleteAudio() {
        try {
            semaphore.acquire()
            semaphore.release()
        } catch (e: InterruptedException) {
            Log.e(TAG, "waitCompleteAudio", e)
            e.printStackTrace()
        }
    }

    /**
     * When the player is ready to play if going to play the audio
     *
     * @param player the player that is going to play the audio itself
     */
    override fun onPrepared(player: MediaPlayer) {
        player.start()
    }

    /**
     * @param mediaPlayer the MediaPlayer that reached the end of the file
     */
    override fun onCompletion(mediaPlayer: MediaPlayer) {
        semaphore.release()
    }

    /**
     * @return if the audio delegator is prepare to play audio or not
     */
    override fun isNotPreparedToPlay(): Boolean {
        return !preparedToPlay
    }

    /**
     * Sets the used variables to null in order to free resources
     */
    override fun dispose() {
        mediaPlayer.release()
        preparedToPlay = false
    }
}