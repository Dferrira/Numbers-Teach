package com.dferreira.numbers_teach.ui_models;


/**
 * Model that describes the exercise activities
 */
public class ActivityItem {

    /**
     * Resource id of the icon to show in the list
     */
    public int iconResourceId;

    /**
     * Resource id of the label used
     */
    public int labelResourceId;
    public Class<?> activityToLaunch;
    public boolean showMenu;
}
