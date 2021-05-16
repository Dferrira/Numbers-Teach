package com.dferreira.numbers_teach.numbers.models

import android.content.Context
import com.dferreira.numbers_teach.commons.IGenericStudySet

class StudySetFactory {
    fun createStudySet(context: Context): IGenericStudySet {
        val applicationContext = context.applicationContext
        return StudySet(applicationContext)
    }


}