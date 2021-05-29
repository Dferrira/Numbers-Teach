package com.dferreira.numbers_teach.ui_models

/**
 * Model that describes the exercise activities
 */
data class ActivityItem(
    val iconResourceId: Int,
    val labelResourceId: Int,
    val activityToLaunch: Class<*>,
    val showMenu: Boolean
)