package com.dferreira.numbers_teach.select_images_exercise;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dferreira.numbers_teach.NumberTeachApplication;
import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.commons.GenericStudySet;
import com.dferreira.numbers_teach.commons.IGenericStudySet;
import com.dferreira.numbers_teach.delegators.AudioDelegator;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.generic_exercise.ExerciseMsgType;
import com.dferreira.numbers_teach.generic_exercise.UserActionMsgProvider;
import com.dferreira.numbers_teach.helpers.ExercisesHelper;
import com.dferreira.numbers_teach.repositories.ExerciseResultRepository;

import java.util.Date;

/**
 * Go through the list of audio
 */
public class SelectImagesExerciseService extends IntentService {


    private static final String TAG = "SelectImagesExerciseService";
    public static final String NOTIFICATION = "com.dferreira.numbers_teach.exercise.receiver";

    /**
     * Key the is going to be used to send the name of the activity that called
     * the service started
     */
    public static final String ACTIVITY_NAME = "activityName";

    /**
     * Key that is going to be used to indicate the language picked by the user
     * when the service is started
     */
    public static final String LANGUAGE = "Language";

    /**
     * Key that is going to be used to indicate what type of exercise is
     */
    public final static String TYPE_OF_EXERCISE = "typeOfExercise";


    public static final String TYPE_KEY = "Type";

    /**
     * Key that indicates if the answer of the user was correct or not
     */
    public static final String CORRECT_KEY = "Correct";

    private static final Integer ACTIVITY_TIMEOUT = 2000;
    private static final int NUMBER_OPTIONS = 4;
    private static final int NUMBER_OF_SLIDES = 20;


    /**
     * Key of the element score of the user in the end of the exercise
     */
    public static final String SCORE_KEY = "total";

    /**
     * Key of the label element
     */
    public static final String LABEL_KEY = "label";


    /**
     * key of the image 2D element
     */
    public static final String IMAGES2D_KEY = "images2D";

    /**
     * key of the tags of the images
     */
    public static final String TAGS_KEY = "tags";

    /**
     * Class responsible by play audio
     */
    private final AudioDelegator audioDelegator;

    //Indicates if the service is already running or not
    private static boolean running = false;

    private static Long activityPaused;

    private static boolean toRestoreState;
    private static Integer lastKnowValue;
    private static int score;

    private int lastIndexPlayed;

    /*Name of the activity that started the service*/
    private String activityName;

    /*Language of the audio to play*/
    private String language;


    /*Indicate what type of exercise is*/
    private ExerciseType exerciseType;

    /*Indicate if should show the text or not*/
    private boolean withText;

    /*Number of slides to show in the label UI*/
    private int totalSlides;

    /*Index to show to the user of the slides*/
    private final int[] indexes;

    /**
     * Reference to the study set that is going to be used
     * to provide the set of resources
     */
    private final IGenericStudySet studySet;

    /**
     * Starts the sequence audio handler
     */
    public SelectImagesExerciseService() {
        super(TAG);
        SelectImagesExerciseService.running = true;
        SelectImagesExerciseService.activityPaused = null;
        SelectImagesExerciseService.toRestoreState = true;

        this.audioDelegator = new AudioDelegator(this);
        this.audioDelegator.prepareToPlay();

        this.lastIndexPlayed = -1;
        this.indexes = ExercisesHelper.generateIndexes(0, NUMBER_OF_SLIDES, NUMBER_OF_SLIDES);
        this.studySet = NumberTeachApplication.getStudySetInstance();

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
    }

    /**
     * Indicates to the service that should not keep running when comes back
     */
    public static void disableToRestoreState() {
        SelectImagesExerciseService.toRestoreState = false;
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
     * Send the information to the UI to render the current slide.
     *
     * @param playingIndex Index of the audio to play
     */
    private void sendCurrentSlideUINotification(int playingIndex) {
        String label = studySet.getAudioLabel(this.language, playingIndex);
        int[] selectableIndexes = ExercisesHelper.generateIndexesOptions(playingIndex, totalSlides, NUMBER_OPTIONS);
        String[] images2DPath = studySet.getImagesPath(selectableIndexes);
        Object[] tags = ExercisesHelper.getTags(selectableIndexes);
        this.sendUpdateListOfChoices(label, images2DPath, tags);
    }

    /**
     * Play a single slide
     *
     * @param playingIndex Index of the audio to play
     */
    private synchronized void playSingleAudio(int playingIndex) {
        if ((lastIndexPlayed != playingIndex) && (playingIndex < this.totalSlides)) {
            int randomIndex = indexes[playingIndex];
            this.sendCurrentSlideUINotification(randomIndex);
            if (exerciseType.isWithAudio()) {
                String audioPath = studySet.getAudioPath(this.language, randomIndex);
                this.audioDelegator.playPath(audioPath);
                this.audioDelegator.waitCompleteAudio();
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
     * Is going to evaluate if the user have made a correct choice or not
     *
     * @param intent       intent with tag which maths with image that was selected by the user
     * @param playingIndex index that the user is listening
     * @return The audio that should play
     */
    private int evaluateUserChoice(Intent intent, int playingIndex) {
        Object selectedTag = UserActionMsgProvider.getTag(intent);
        Object currentTag = ExercisesHelper.getTag(indexes[playingIndex]);
        if (selectedTag.equals(currentTag)) {
            sendResultOfUserChoice(true);
            ++score;
            return playingIndex + 1;
        } else {
            sendResultOfUserChoice(false);
            if (score > 0) {
                --score;
            }
            return playingIndex;
        }
    }

    /**
     * Handle the input/output of the user
     *
     * @param playingIndex Index of the audio to play
     * @return THe index that should play
     */
    private synchronized int handleUserIO(int playingIndex) {
        Intent intent = UserActionMsgProvider.getInstance().popIntent();
        if (intent == null) {
            //Nothing to do is going to wait
            try {
                wait(200);
            } catch (InterruptedException ignored) {
            }
            return playingIndex;
        } else {
            switch (UserActionMsgProvider.getMsgType(intent)) {
                case OPTION_SELECT:
                    return evaluateUserChoice(intent, playingIndex);
                case REPLAY_AUDIO:
                    if (exerciseType.isWithAudio()) {
                        replayAudio();
                    }
                    return playingIndex;
                default:
                    return playingIndex;
            }
        }
    }

    /**
     * Save in database the score of the user
     */
    private void saveLastResultInDatabase() {
        ExerciseResultRepository exerciseResultRepository = new ExerciseResultRepository(this);
        exerciseResultRepository.saveScore(activityName, language, score, NUMBER_OF_SLIDES, exerciseType);
    }

    /**
     * Main loop of the service of exercise to select images
     */
    private void mainLoop() {
        if (!TextUtils.isEmpty(language)) {

            int startIndex = 0;
            if (toRestoreState && (lastKnowValue != null)) {
                startIndex = lastKnowValue;
                lastKnowValue = null;
            } else {
                score = 0;
            }
            this.totalSlides = studySet.getNumberCount();

            int playingIndex = startIndex;

            while (true) {
                if (playingIndex == this.totalSlides) {
                    String finalScoreStr = this.getResources().getString(R.string.final_score);
                    saveLastResultInDatabase();
                    sendShowScore(finalScoreStr + Integer.toString(score));
                    stopSelf();
                    break;
                }

                //Play a single audio


                playSingleAudio(playingIndex);


                playingIndex = handleUserIO(playingIndex);


                //If detects that the activity was paused will pause also the service
                if (stopSelfIfActivityPaused()) {
                    if (toRestoreState) {
                        lastKnowValue = playingIndex;
                    }
                    stopSelf();
                    break;
                }
            }
            sendUIFinishingRequest();
        }
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

        this.activityName = intent.getStringExtra(ACTIVITY_NAME);
        this.language = intent.getStringExtra(LANGUAGE);
        this.exerciseType = (ExerciseType) intent.getSerializableExtra(TYPE_OF_EXERCISE);


        mainLoop();

        this.audioDelegator.dispose();


        running = false;
    }

    /**
     * Replay once again the last asset
     */
    private void replayAudio() {
        if (this.audioDelegator.isNotPreparedToPlay()) {
            this.audioDelegator.prepareToPlay();
        }
        this.audioDelegator.replayLastAsset();
    }

    /**
     * Show to the user if the choice made was correct or not
     *
     * @param correct false the user have chosen the wrong option
     *                true the user have chosen the correct option
     */
    private void sendResultOfUserChoice(boolean correct) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(CORRECT_KEY, correct);
        intent.putExtra(TYPE_KEY, ExerciseMsgType.SHOW_RESULT_OF_CHOICE);
        sendBroadcast(intent);
    }

    /**
     * Notification the broadcast receivers that should update their UI
     *
     * @param label        label to show in the ui
     * @param images2DPath path of the image to show in the UI (case 3D was not supported
     * @param tags         tags of the options available in the palette
     */
    private void sendUpdateListOfChoices(String label, String[] images2DPath, Object[] tags) {
        Intent intent = new Intent(NOTIFICATION);
        if (withText) {
            intent.putExtra(LABEL_KEY, label);
        }
        intent.putExtra(IMAGES2D_KEY, images2DPath);
        intent.putExtra(TAGS_KEY, tags);
        intent.putExtra(TYPE_KEY, ExerciseMsgType.UPDATE_LIST_OF_CHOICES);
        sendBroadcast(intent);
    }

    /**
     * Set in the UI the last score of the user
     *
     * @param finalScore string with final score to show to the user
     */
    private synchronized void sendShowScore(String finalScore) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(SCORE_KEY, finalScore);
        intent.putExtra(TYPE_KEY, ExerciseMsgType.SHOW_SCORE);
        sendBroadcast(intent);
    }

    /**
     * Send the message that finish the activity and go back to the previous one
     */
    private void sendUIFinishingRequest() {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(TYPE_KEY, ExerciseMsgType.FINISHING_ACTIVITY);
        sendBroadcast(intent);
    }
}
