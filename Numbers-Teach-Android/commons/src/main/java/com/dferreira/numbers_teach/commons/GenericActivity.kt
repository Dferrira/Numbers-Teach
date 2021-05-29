package com.dferreira.numbers_teach.commons

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

/**
 * Contain stuff that is common to the all activities of this app
 */
abstract class GenericActivity : AppCompatActivity() {
    /**
     * Setup the elements of the action bar of the activity
     */
    protected fun setupActionBar(iconResourceId: Int) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar ?: return
        //Show the back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        val titleStr = actionBar.title
        if (titleStr.isNullOrBlank()) {
            return
        }
        actionBar.title = null
        val title = findViewById<AppCompatTextView>(R.id.toolbar_title)
        title?.text = titleStr
        val icon = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        icon?.setImageResource(iconResourceId)
    }
}