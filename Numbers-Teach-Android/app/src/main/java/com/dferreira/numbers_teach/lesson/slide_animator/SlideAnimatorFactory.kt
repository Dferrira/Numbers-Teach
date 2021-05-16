package com.dferreira.numbers_teach.lesson.slide_animator

import android.content.Context
import android.widget.ViewFlipper

class SlideAnimatorFactory {
    fun createSlideAnimator(context: Context, viewFlipper: ViewFlipper): ISlideAnimator {
        val applicationContext = context.applicationContext;
        return LessonSlideAnimator(applicationContext, viewFlipper)
    }
}