package com.dferreira.numbers_teach.europe_dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.activities_list.ViewHolder;
import com.dferreira.numbers_teach.helpers.ImageHelper;
import com.dferreira.numbers_teach.scores.ScoresListActivity;
import com.dferreira.numbers_teach.select_images_exercise.SelectImagesExerciseActivity;
import com.dferreira.numbers_teach.sequence.SequenceActivity;
import com.dferreira.numbers_teach.ui_models.ActivityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * List adapter that is going to be responsible for provide a list of activities to the user learn the language
 */
public class CountriesListAdapter extends ArrayAdapter<ViewHolder> implements View.OnClickListener {
    private final static String LANGUAGE_KEY = "language";

    private final List<ActivityItem> activities;
    private final LayoutInflater inflater;
    private final String language;

    /**
     *
     * @param context Context where the list of activities will be used
     * @param language Language that was selected by the user to use
     */
    public CountriesListAdapter(Context context, String language) {
        super(context, R.layout.activity_item);
        this.inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activities = new ArrayList<>();
        this.language = language;

        //Sequence of cards
        ActivityItem cards = new ActivityItem();
        cards.iconResourceId = R.mipmap.sequence_of_slides_icon;
        cards.labelResourceId = R.string.cards;
        cards.activityToLaunch = SequenceActivity.class;

        //Select images
        ActivityItem selectImages = new ActivityItem();
        selectImages.iconResourceId = R.mipmap.select_image_exercise_icon;
        selectImages.labelResourceId = R.string.match;
        selectImages.activityToLaunch = SelectImagesExerciseActivity.class;

        //Show the list of scores in the application
        ActivityItem results = new ActivityItem();
        results.iconResourceId = R.mipmap.score_icon;
        results.labelResourceId = R.string.results;
        results.activityToLaunch = ScoresListActivity.class;

        this.activities.add(cards);
        this.activities.add(selectImages);
        this.activities.add(results);
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Pair<ViewHolder, ActivityItem> tag;

        ActivityItem item = activities.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            tag = new Pair<>(holder,item);
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
    @Override
    public void onClick(View v) {
        Pair<ViewHolder, ActivityItem> tag = (Pair<ViewHolder, ActivityItem>)v.getTag();
        ActivityItem item = tag.second;

        Intent intent = new Intent(getContext(), item.activityToLaunch);
        intent.putExtra(LANGUAGE_KEY, language);
        getContext().startActivity(intent);
    }
}
