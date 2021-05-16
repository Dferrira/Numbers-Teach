package com.dferreira.numbers_teach.lesson.slide_animator

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import com.dferreira.numbers_teach.R

/**
 * Treats to put the slider view flipper view in the right slid using some animation for that
 */
class LessonSlideAnimator(context: Context, private val flipper: ViewFlipper) :
    ISlideAnimator {
    private val inFromRight: Animation = AnimationUtils.loadAnimation(
        context,
        R.anim.in_from_right
    )
    private val outToLeft: Animation = AnimationUtils.loadAnimation(context, R.anim.out_to_left)
    private val inFromLeft: Animation = AnimationUtils.loadAnimation(context, R.anim.in_from_left)
    private val outToRight: Animation = AnimationUtils.loadAnimation(context, R.anim.out_to_right)

    /**
     * Set the animations from the slide going to the next one
     */
    override fun showNext() {
        flipper.inAnimation = inFromRight
        flipper.outAnimation = outToLeft
        flipper.showNext()
    }

    /**
     * Set the animation when the user is going to the previous slide
     */
    override fun showPrevious() {
        flipper.inAnimation = inFromLeft
        flipper.outAnimation = outToRight
        flipper.showPrevious()
    }

}