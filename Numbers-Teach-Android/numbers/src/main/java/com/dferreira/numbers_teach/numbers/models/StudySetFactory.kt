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

    public fun CreateStudySet(context: Context): IGenericStudySet {

        return StudySet(context)
    }


}