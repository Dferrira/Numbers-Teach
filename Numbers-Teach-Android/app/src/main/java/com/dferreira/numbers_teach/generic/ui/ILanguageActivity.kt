package com.dferreira.numbers_teach.generic.ui

/**
 * Interface that should be implemented by the activities in order to provide the information
 * to the its fragments which was the language selected by the user
 */
interface ILanguageActivity {

    /**
     * Get the language the was selected by the user
     */
    val languagePrefix: String
}