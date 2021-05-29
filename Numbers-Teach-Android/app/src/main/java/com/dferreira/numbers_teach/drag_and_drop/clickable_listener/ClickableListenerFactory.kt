package com.dferreira.numbers_teach.drag_and_drop.clickable_listener

import android.view.View
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice

class ClickableListenerFactory {
    fun createClickableListener(target: ISelectedChoice): View.OnClickListener {
        return ClickableListenerImpl(target)
    }
}