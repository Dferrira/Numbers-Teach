package com.dferreira.numbers_teach.lesson;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.ui.ISequenceHandler;

/**
 * Treats to put the slider view flipper view in the right slid using some animation for that
 */
public class LessonSlideAnimator implements ISequenceHandler {
    private final ViewFlipper flipper;
    private final Animation inFromRight;
    private final Animation outToLeft;
    private final Animation inFromLeft;
    private final Animation outToRight;

    /**
     * @param context global information about an application environment.
     * @param flipper Animate between the transition of his children
     */
    public LessonSlideAnimator(Context context, ViewFlipper flipper) {
        this.flipper = flipper;
        this.inFromRight = AnimationUtils.loadAnimation(context,
                R.anim.in_from_right);
        this.outToLeft = AnimationUtils.loadAnimation(context, R.anim.out_to_left);
        this.inFromLeft = AnimationUtils.loadAnimation(context, R.anim.in_from_left);
        this.outToRight = AnimationUtils.loadAnimation(context, R.anim.out_to_right);

    }

    /**
     * Set the animations from the slide going to the next one
     */
    public void forward() {
        this.flipper.setInAnimation(this.inFromRight);
        this.flipper.setOutAnimation(this.outToLeft);
        this.flipper.showNext();
    }

    /**
     * Set the animation when the user is going to the previous slide
     */
    public void previous() {
        this.flipper.setInAnimation(this.inFromLeft);
        this.flipper.setOutAnimation(this.outToRight);
        this.flipper.showPrevious();
    }

}
