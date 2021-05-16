package com.dferreira.numbers_teach.numbers.models

import android.content.Context
import com.dferreira.numbers_teach.commons.IGenericStudySet

class StudySetFactory {
    private val languagesPrefix: List<String>

    init {
        languagesPrefix = listOf(
            "en", "fr", "de", "pt", "lb"
        )
    }

    public fun createStudySet(context: Context): IGenericStudySet {
        val applicationContext = context.applicationContext
        return StudySet(applicationContext)
    }


}