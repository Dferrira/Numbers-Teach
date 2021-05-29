package com.dferreira.numbers_teach.generic_exercise

/**
 * Interface that should be implemented by the images palette in the UI
 */
interface IImagesPalette {

    fun setDrawablesPaths(paths: Array<String>)

    fun setTags(tags: Array<Any>)

    fun setTarget(target: ISelectedChoice)
}