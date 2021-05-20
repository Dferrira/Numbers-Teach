package com.dferreira.numbers_teach.lesson;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dferreira.numbers_teach.NumberTeachApplication;
import com.dferreira.numbers_teach.commons.IGenericStudySet;
import com.dferreira.numbers_teach.delegators.AudioDelegatorFactory;
import com.dferreira.numbers_teach.delegators.IAudioDelegator;
import com.dferreira.numbers_teach.preferences.PreferencesUtils;

import java.util.Date;

/**
 * Go through the list of audio
 */
public class LessonService extends IntentService {


    public static final String NOTIFICATION = "com.dferreira.numbers_teach.sequence.receiver";
    public static final String LANGUAGE = "Language";
    public static final String TYPE_KEY = "Type";
    /**
     * Key of the index element
     */
    public static final String INDEX_KEY = "index";
    /**
     * Key of the element with a counter of all supported slides
     */
    public static final String TOTAL_KEY = "total";
    /**
     * Key of the label element
     */
    public static final String LABEL_KEY = "label";
    /**
     * key of the image
     */
    public static final String IMAGE_KEY = "image";
    private static final String TAG = "SelectImagesExerciseService";
    private static final Integer ACTIVITY_TIMEOUT = 1000;
    //Indicates if the service is already running or not
    private static boolean running = false;
    private static Long activityPaused;
    private static boolean toRestoreState;
    private static Integer lastKnowValue;
    private static LessonService instance;
    /**
     * Reference to the study set that is going to be used
     * to provide the set of resources
     */
    private final IGenericStudySet studySet;
    private final IAudioDelegator audioDelegator;
    /*Indicates if the service is playing sequentially the audio*/
    private boolean isPlaying;
    private int playingIndex;
    private int lastIndexPlayed;
    /*Language of the audio to play*/
    private String language;
    /*Number of slides to show in the label UI*/
    private int totalSlides;

    /**
     * Starts the sequence audio handler
     */
    public LessonService() {
        super(TAG);
        LessonService.running = true;
        LessonService.activityPaused = null;
        AudioDelegatorFactory audioDelegatorFactory = new AudioDelegatorFactory();
        this.audioDelegator = audioDelegatorFactory.createAudioDelegator(this);
        this.audioDelegator.prepareToPlay();
        LessonService.toRestoreState = true;
        this.isPlaying = true;
        this.lastIndexPlayed = -1;
        this.studySet = NumberTeachApplication.Companion.getStudySetInstance();
        LessonService.instance = this;
    }

    public static LessonService getInstance() {
        return instance;
    }

    /**
     * @return If the service is already running or not
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * Set the activity paused time
     *
     * @param lActivityPaused When the activity was paused
     */
    public static void setActivityPaused(Long lActivityPaused) {
        activityPaused = lActivityPaused;
        if (getInstance() != null) {
            getInstance().handleScreenRotation();
        }
    }

    /**
     * Indicates to the service that should not keep running when comes back
     */
    public static void disableToRestoreState() {
        LessonService.toRestoreState = false;
    }

    /**
     * Handle rotation of screen and paused
     */
    private void handleScreenRotation() {
        if ((LessonService.activityPaused == null) || (!isPlaying())) {
            this.sendCurrentSlideUINotification();
            this.reload();
        }
    }

    /**
     * Stop the service if the activity associated was stopped
     */
    private boolean stopSelfIfActivityPaused() {
        if (activityPaused == null) {
            return false;
        } else {
            long now = new Date().getTime();
            return (now - activityPaused) > ACTIVITY_TIMEOUT;
        }
    }

    /**
     * @return (false) The user complete controls the slide sliding
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set if sequence of slides or not
     *
     * @param isPlaying Flag if is showing images one-by-one like a movie playing
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
        sendChangePlayPauseChangeRequest();
    }

    /**
     * Send the information to the UI to render the current slide.
     */
    private void sendCurrentSlideUINotification() {
        String label = studySet.getAudioLabel(this.language, playingIndex);
        String imagePath = studySet.getImagePath(playingIndex);
        this.sendUIRefreshRequest(playingIndex, this.totalSlides, label, imagePath);
    }

    /**
     * Play a single slide
     */
    private synchronized void playSingleAudio() {
        if ((lastIndexPlayed != playingIndex) && (playingIndex < this.totalSlides)) {
            String audioPath = studySet.getAudioPath(this.language, playingIndex);
            this.sendCurrentSlideUINotification();
            this.audioDelegator.playPath(audioPath);
            this.audioDelegator.waitCompleteAudio();
            try {
                int waitTime = PreferencesUtils.getDelayBetweenSlides(this);
                if (waitTime != 0) {
                    wait(waitTime);
                }
            } catch (InterruptedException ignored) {

            }
            this.lastIndexPlayed = playingIndex;
        } else {
            try {
                wait(1000);
            } catch (InterruptedException ignored) {
            }
        }

    }

    /**
     * Is playing should play one by one the audio
     *
     * @param startIndex index that is going the first one ear/show to the user
     */
    private int playAudioSequence(int startIndex) {
        for (playingIndex = startIndex; playingIndex < totalSlides; playingIndex++) {
            playSingleAudio();
            if (stopSelfIfActivityPaused()) {
                if (toRestoreState) {
                    lastKnowValue = playingIndex + 1;
                }
                stopSelf();
                break;
            }
            if (!isPlaying) {
                startIndex = playingIndex;
                break;
            }
        }
        return startIndex;
    }

    /**
     * @return index of the audio that played
     */
    private int playSingleSlide() {
        playSingleAudio();
        return playingIndex;
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        this.language = intent.getStringExtra(LANGUAGE);


        if (!TextUtils.isEmpty(language)) {

            int startIndex = 0;
            if (toRestoreState && (lastKnowValue != null)) {
                startIndex = lastKnowValue;
                lastKnowValue = null;
            }
            this.totalSlides = studySet.getCounter(this.language);


            while (true) {
                if (isPlaying) {
                    startIndex = playAudioSequence(startIndex);
                    //All the slides were played so should return
                    if (isPlaying) {
                        break;
                    }
                } else {
                    //Play a single audio
                    startIndex = playSingleSlide();
                    if (stopSelfIfActivityPaused()) {
                        if (toRestoreState) {
                            lastKnowValue = playingIndex;
                        }
                        stopSelf();
                        break;
                    }
                }
            }
            sendUIFinishingRequest();
        }

        this.audioDelegator.dispose();
        running = false;
        playingIndex = 0;
    }

    /**
     * Go to the previous slide
     */
    public void previous() {
        if (this.audioDelegator.isNotPreparedToPlay()) {
            this.audioDelegator.prepareToPlay();
        }
        if (this.playingIndex != 0) {
            this.playingIndex--;
        }
    }

    /**
     * Go to the next slide
     */
    public void next() {
        if (this.audioDelegator.isNotPreparedToPlay()) {
            this.audioDelegator.prepareToPlay();
        }
        if (isPlaying) {
            this.audioDelegator.stopAudio();
        } else {
            this.audioDelegator.stopAudio();
            this.playingIndex++;
        }
    }

    /**
     * Replay once again the last asset
     */
    public void reload() {
        if (this.audioDelegator.isNotPreparedToPlay()) {
            this.audioDelegator.prepareToPlay();
        }
        this.audioDelegator.replayLastAsset();
    }

    /**
     * Notification the broadcast receivers that should update their UI
     *
     * @param index     Index of the audio that is going to be read
     * @param total     Total of slides supported
     * @param label     label to show in the ui
     * @param imagePath path of the image to show in the UI
     */
    private void sendUIRefreshRequest(int index, int total, String label, String imagePath) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(INDEX_KEY, index);
        intent.putExtra(TOTAL_KEY, total);
        intent.putExtra(LABEL_KEY, label);
        intent.putExtra(IMAGE_KEY, imagePath);
        intent.putExtra(TYPE_KEY, LessonBroadcastMsgType.UPDATE_SLIDE_VIEW);
        sendBroadcast(intent);
    }

    /**
     * Send the message that finish to play all the slides that were assigned
     */
    private void sendUIFinishingRequest() {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(TYPE_KEY, LessonBroadcastMsgType.FINISHING_ACTIVITY);
        sendBroadcast(intent);
    }

    /**
     * Send the message that the play/pause status changed
     */
    private void sendChangePlayPauseChangeRequest() {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(TYPE_KEY, LessonBroadcastMsgType.CHANGE_PLAYING_STATE);
        sendBroadcast(intent);
    }

}
