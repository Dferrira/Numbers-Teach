package com.dferreira.numbers_teach.lesson;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dferreira.numbers_teach.generic.ui.DeviceStretcher;
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.generic.ui.IPlayHandler;
import com.dferreira.numbers_teach.generic.ui.ISequenceHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the calls of the audio service
 */
public class LessonBroadcastReceiver extends BroadcastReceiver {

    private final ILabeledHandler labeledView;
    private final IPlayHandler playView;
    private final ISequenceHandler sequenceHandler;
    private final Activity activity;
    private final DeviceStretcher deviceStretcher;
    private List<TextView> imgDescriptions;
    private int previousIndex;


    /**
     * @param activity  Activity where the got the sequence broad cast
     * @param flipper   Flipper view that is going to be forward or back in when the notification is received
     * @param labelView Interface to the place where should put the value of the label
     * @param playView  Interface to update the state of the button play and pause
     */
    public LessonBroadcastReceiver(Activity activity, ViewFlipper flipper, ILabeledHandler labelView, IPlayHandler playView) {
        this.labeledView = labelView;
        this.playView = playView;
        this.sequenceHandler = new LessonSlideAnimator(activity, flipper);
        this.previousIndex = 0;
        this.activity = activity;

        this.deviceStretcher = new DeviceStretcher(activity);
    }

    /**
     * @param imageDescription1 Place where is going to be put the the first image
     * @param imageDescription2 View where is going to be put the second image
     */
    public void setTextViews(TextView imageDescription1, TextView imageDescription2) {
        this.imgDescriptions = new ArrayList<>();
        this.imgDescriptions.add(imageDescription1);
        this.imgDescriptions.add(imageDescription2);
    }

    /**
     * @param index index of the image to set
     * @param total number total of images that exist in this set
     */
    private void setLabelView(int index, int total) {
        if (this.labeledView != null) {
            this.labeledView.setLabel((index + 1) + "/" + total);
        }
    }

    /**
     * Set the description of the image corresponding to the index provided
     *
     * @param index Index of the slide to set
     * @param label Label to show to the user
     */
    private void setImageDescription(int index, String label) {
        imgDescriptions.get(index % 2).setText(label);
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    private void freeImageCanvas(int index) {
        TextView tv = imgDescriptions.get((index) % 2);
        Drawable[] drawables = tv.getCompoundDrawables();

        for (int i = 0; i < drawables.length; i++) {
            Drawable drawable = drawables[i];
            if (drawable != null) {
                ((BitmapDrawable) drawable).getBitmap().recycle();
            }
        }
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    /**
     * @param index   Index of the image to show to the user
     * @param imgPath Path to the image to show to the user
     */
    private void setImageCanvas(int index, String imgPath) {
        InputStream imgStream = null;

        try {
            imgStream = activity.getAssets().open(imgPath);
            if (imgStream != null) {
                Drawable transImg = deviceStretcher.zoomDrawable(imgStream);
                imgDescriptions.get(index % 2).setCompoundDrawablesWithIntrinsicBounds(null, null, null, transImg);
                freeImageCanvas(index + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (imgStream != null) {
                try {
                    imgStream.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    /**
     * Set the description of the image corresponding to the index provided
     *
     * @param index Index of the slide to set
     */
    private void goToSlide(int index) {
        //If the new index bigger needs to forward
        if (previousIndex < index) {
            for (int i = 0; i < (index - previousIndex); i++) {
                this.sequenceHandler.forward();
            }
        }

        //If the new index smaller need to go back
        if (previousIndex > index) {
            for (int i = 0; i < (previousIndex - index); i++) {
                this.sequenceHandler.previous();
            }
        }

        previousIndex = index;
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
            LessonBroadcastMsgType msgType = (LessonBroadcastMsgType) intent.getSerializableExtra(LessonService.TYPE_KEY);

            switch (msgType) {
                case UPDATE_SLIDE_VIEW:
                    int index = intent.getIntExtra(LessonService.INDEX_KEY, 0);
                    int total = intent.getIntExtra(LessonService.TOTAL_KEY, 0);
                    String label = intent.getStringExtra(LessonService.LABEL_KEY);
                    String img2DPath = intent.getStringExtra(LessonService.IMAGE_KEY);

                    this.setLabelView(index, total);

                    setImageCanvas(index, img2DPath);
                    goToSlide(index);

                    setImageDescription(index, label);
                    break;
                case FINISHING_ACTIVITY:
                    this.activity.onBackPressed();
                    break;
                case CHANGE_PLAYING_STATE:
                    this.playView.updatePlayPauseButtons();
                    break;
            }
        }
    }
}