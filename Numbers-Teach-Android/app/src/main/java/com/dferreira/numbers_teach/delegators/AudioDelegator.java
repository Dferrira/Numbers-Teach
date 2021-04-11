package com.dferreira.numbers_teach.delegators;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Responsible for play the audio
 */
public class AudioDelegator implements MediaPlayer.OnPreparedListener, OnCompletionListener {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "AudioDelegator";
    /**
     * Number of seconds that will wait until thin that was not possible to
     * play an audio
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final int AUDIO_TIMEOUT = 5;
    private final Context context;
    private MediaPlayer mp;
    private Semaphore semaphore;
    private boolean preparedToPlay;


    /**
     * Will pass the required information to to reproduce an audio
     *
     * @param context context where the AudioDelegator was called
     */
    public AudioDelegator(Context context) {
        this.context = context;
        this.preparedToPlay = false;
    }

    /**
     * Allocates all the resources required to play the given medias
     */
    public void prepareToPlay() {
        this.mp = new MediaPlayer();
        this.mp.setOnPreparedListener(this);
        this.mp.setOnCompletionListener(this);
        this.mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.semaphore = new Semaphore(1);
        this.preparedToPlay = true;
    }


    /**
     * @param filePath File path of the file to open
     * @return The assetFileDescriptor of the file in the specified path
     * @throws IOException If was not possible open the specified file
     */
    private AssetFileDescriptor getFileDescriptor(String filePath) throws IOException {
        AssetManager assetManager = context.getAssets();
        return assetManager.openFd(filePath);
    }


    /**
     * Reproduce an audio file
     *
     * @param filePath File path of the file to play
     *                 true    -> everything was fine
     */
    public void playPath(String filePath) {
        try {

            mp.reset();
            AssetFileDescriptor assetDescriptor = getFileDescriptor(filePath);
            mp.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
            mp.prepareAsync();
            semaphore.tryAcquire(AUDIO_TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "playPath", e);
        }
    }

    /**
     * Will re-play the las asset
     */
    public void replayLastAsset() {
        mp.seekTo(0);
        if (!mp.isPlaying()) {
            mp.start();
        }
    }

    /**
     * Will stop the reproduction of an audio
     */
    public void stopAudio() {
        if (mp.isPlaying()) {
            mp.seekTo(mp.getDuration());
        }
    }

    /**
     * Forces the thread where the method is called to wait until the file that specified to end
     */
    public void waitCompleteAudio() {
        try {
            semaphore.acquire();
            semaphore.release();
        } catch (InterruptedException e) {
            Log.e(TAG, "waitCompleteAudio", e);
            e.printStackTrace();
        }
    }

    /**
     * When the player is ready to play if going to play the audio
     *
     * @param player the player that is going to play the audio itself
     */
    @Override
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    /**
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (this.semaphore != null) {
            this.semaphore.release();
        }
    }

    /**
     * @return if the audio delegator is prepare to play audio or not
     */
    public boolean isNotPreparedToPlay() {
        return !preparedToPlay;
    }

    /**
     * Sets the used variables to null in order to free resources
     */
    public void dispose() {
        this.mp.release();
        this.mp = null;
        this.semaphore = null;
        this.preparedToPlay = false;
    }
}