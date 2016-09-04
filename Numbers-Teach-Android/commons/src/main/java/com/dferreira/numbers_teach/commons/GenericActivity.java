package com.dferreira.numbers_teach.commons;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

/**
 * Contain stuff that is common to the all activities of this app
 */
public abstract class GenericActivity extends AppCompatActivity {

    /**
     * Setup the elements of the action bar of the activity
     */
    protected void setupActionBar(Integer iconResourceId) {

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Show the back button
            actionBar.setDisplayHomeAsUpEnabled(true);

            //Change the order of the image
            if (iconResourceId != null) {
                CharSequence titleStr = actionBar.getTitle();
                if (!TextUtils.isEmpty(titleStr)) {
                    actionBar.setTitle(null);
                    AppCompatTextView title = (AppCompatTextView) findViewById(R.id.toolbar_title);
                    AppCompatImageView icon = (AppCompatImageView) findViewById(R.id.toolbar_icon);
                    if (title != null) {
                        title.setText(titleStr);
                    }
                    if (icon != null) {
                        icon.setImageResource(iconResourceId);
                    }
                }
            }
        }
    }
}
