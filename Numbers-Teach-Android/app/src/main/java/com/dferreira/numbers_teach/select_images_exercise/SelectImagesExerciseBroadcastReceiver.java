package com.dferreira.numbers_teach.select_images_exercise;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.generic_exercise.ExerciseMsgType;
import com.dferreira.numbers_teach.generic_exercise.IImagesPalette;
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice;

/**
 * Handles the calls of the images selector service
 */
public class SelectImagesExerciseBroadcastReceiver extends BroadcastReceiver implements View.OnClickListener {

    private final Activity activity;
    /*UI variables*/
    private final IImagesPalette imagesPalette;
    private final ISelectedChoice selectedChoice;
    private final ILabeledHandler labeledView;


    /**
     * @param activity      Activity where the got the sequence broad cast
     * @param imagesPalette Images palette where the different options of images are going to be load
     * @param labelView     Interface to the place where should put the value of the label (Or the current label)
     */
    public SelectImagesExerciseBroadcastReceiver(Activity activity, IImagesPalette imagesPalette, ISelectedChoice selectedChoice, ILabeledHandler labelView) {
        this.activity = activity;
        this.imagesPalette = imagesPalette;
        this.selectedChoice = selectedChoice;
        this.labeledView = labelView;
    }

    /**
     * Set in the UI the label corresponding to the right option
     *
     * @param label Label to set in the UI corresponding to the right option
     */
    private void setLabelView(String label) {
        if (this.labeledView != null) {
            this.labeledView.setLabel(label);
        }
    }

    /**
     * Cleans the selected view between slides
     */
    private void resetViews() {
        if (this.selectedChoice != null) {
            this.selectedChoice.resetViews();
        }
    }


    /**
     * @param imagesPath Path to the images to show in the palette
     * @param tags       Tags of of the images to show in the palette
     */
    private void setPaletteOfOptions(String[] imagesPath, Object[] tags) {

        if ((imagesPalette != null) && (imagesPath.length > 0)) {
            imagesPalette.setDrawablesPaths(imagesPath);
            imagesPalette.setTags(tags);
        }
    }

    /**
     * +
     * Called when something is to be send to the broadcast receivers
     *
     * @param context Context where the notification was trigger
     * @param intent  intent with information of the trigger method
     */
    @Override
    public synchronized void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ExerciseMsgType msgType = (ExerciseMsgType) intent.getSerializableExtra(SelectImagesExerciseService.TYPE_KEY);

            switch (msgType) {
                case UPDATE_LIST_OF_CHOICES:
                    String label = intent.getStringExtra(SelectImagesExerciseService.LABEL_KEY);
                    String[] images2DPath = intent.getStringArrayExtra(SelectImagesExerciseService.IMAGES2D_KEY);
                    Object[] tags = (Object[]) intent.getSerializableExtra(SelectImagesExerciseService.TAGS_KEY);
                    if (!TextUtils.isEmpty(label)) {
                        setLabelView(label);
                    }
                    resetViews();
                    setPaletteOfOptions(images2DPath, tags);
                    break;
                case SHOW_RESULT_OF_CHOICE:
                    boolean correct = intent.getBooleanExtra(SelectImagesExerciseService.CORRECT_KEY, false);
                    if (correct) {
                        this.selectedChoice.showCorrectChoice();
                    } else {
                        this.selectedChoice.showWrongChoice();
                    }
                    break;
                case SHOW_SCORE:
                    String finalScore = intent.getStringExtra(SelectImagesExerciseService.SCORE_KEY);
                    Toast.makeText(context, finalScore, Toast.LENGTH_LONG).show();
                    break;
                case FINISHING_ACTIVITY:
                    this.activity.onBackPressed();
                    break;
            }
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
