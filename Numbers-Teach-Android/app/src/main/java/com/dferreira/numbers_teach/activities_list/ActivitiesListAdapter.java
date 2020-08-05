package com.dferreira.numbers_teach.activities_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.helpers.ImageHelper;
import com.dferreira.numbers_teach.lesson.LessonActivity;
import com.dferreira.numbers_teach.preferences.PreferencesActivity;
import com.dferreira.numbers_teach.scores.GlobalScoresListActivity;
import com.dferreira.numbers_teach.select_images_exercise.SelectImagesExerciseActivity;
import com.dferreira.numbers_teach.ui_models.ActivityItem;

import java.util.ArrayList;
import java.util.List;

import com.dferreira.numbers_teach.languages_dashboard.views.UIHelper;

/**
 * List adapter that is going to be responsible for provide a list of activities to the user learn the language
 */
@SuppressWarnings("WeakerAccess")
public class ActivitiesListAdapter extends ArrayAdapter<ViewHolder> implements View.OnClickListener {
    private final static String LANGUAGE_KEY = "language";

    private final List<ActivityItem> activities;
    private final LayoutInflater inflater;
    private final String language;
    private Menu menu;
    private final Activity activity;

    /**
     * @param activity Context where the list of activities will be used
     * @param language Language that was selected by the user to use
     */
    public ActivitiesListAdapter(Activity activity, String language) {
        super(activity, R.layout.activity_item);
        this.inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activities = new ArrayList<>();
        this.language = language;
        this.activity = activity;

        //Lesson
        ActivityItem lesson = new ActivityItem();
        lesson.iconResourceId = R.mipmap.classroom;
        lesson.labelResourceId = R.string.lesson;
        lesson.activityToLaunch = LessonActivity.class;
        lesson.showMenu = false;

        //Exercises
        ActivityItem exercises = new ActivityItem();
        exercises.iconResourceId = R.mipmap.exercises;
        exercises.labelResourceId = R.string.exercises;
        exercises.activityToLaunch = SelectImagesExerciseActivity.class;
        exercises.showMenu = true;

        //Show the list of scores in the application
        ActivityItem results = new ActivityItem();
        results.iconResourceId = R.mipmap.leaderboard;
        results.labelResourceId = R.string.results;
        results.activityToLaunch = GlobalScoresListActivity.class;
        results.showMenu = false;

        //Show the preferences to the user
        ActivityItem preferences = new ActivityItem();
        preferences.iconResourceId = R.mipmap.ic_settings_black_48dp;
        preferences.labelResourceId = R.string.preferences;
        preferences.activityToLaunch = PreferencesActivity.class;
        preferences.showMenu = false;


        this.activities.add(lesson);
        this.activities.add(exercises);
        this.activities.add(results);
        this.activities.add(preferences);
    }


    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Pair<ViewHolder, ActivityItem> tag;

        ActivityItem item = activities.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            tag = new Pair<>(holder, item);
            convertView.setTag(tag);
            convertView.setOnClickListener(this);
        } else {
            tag = (Pair<ViewHolder, ActivityItem>) convertView.getTag();
        }

        holder = tag.first;

        holder.text.setText(getContext().getText(item.labelResourceId));
        Drawable drawable = ImageHelper.getDrawable(getContext(), item.iconResourceId);
        holder.icon.setImageDrawable(drawable);

        return convertView;
    }

    /**
     * The number of activities to show to the user
     *
     * @return Number of activities
     */
    @Override
    public int getCount() {
        return activities.size();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View v) {
        Pair<ViewHolder, ActivityItem> tag = (Pair<ViewHolder, ActivityItem>) v.getTag();
        ActivityItem item = tag.second;

        menu.setGroupVisible(R.id.exercises_group, item.showMenu);
        if (!item.showMenu) {
            Intent intent = new Intent(getContext(), item.activityToLaunch);
            intent.putExtra(LANGUAGE_KEY, language);
            UIHelper.startNewActivity(this.activity, v, intent);
        }
    }

    /**
     * @param menu Menu that inflated in the activity
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
