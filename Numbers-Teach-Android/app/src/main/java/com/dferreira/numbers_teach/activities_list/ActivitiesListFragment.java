package com.dferreira.numbers_teach.activities_list;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.exercise_icons.views.ExerciseIconsHelper;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity;
import com.dferreira.numbers_teach.select_images_exercise.SelectImagesExerciseActivity;

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
public class ActivitiesListFragment extends Fragment {
    private ListView listOfActivities;
    private ActivitiesListAdapter activitiesListAdapter;


    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of fragment of the list of activities available to the user
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activities_list_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.listOfActivities = (ListView) getActivity().findViewById(R.id.list_of_activities);
    }


    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.activitiesListAdapter = new ActivitiesListAdapter(getActivity(), getLanguage());
        this.listOfActivities.setAdapter(activitiesListAdapter);
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.setHasOptionsMenu(true);

        this.bindViews();
        this.setEvents();
    }


    /**
     * Inflates the existing menu
     *
     * @param menu         Menu to be inflated
     * @param menuInflater Inflater of the menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.activitiesListAdapter.setMenu(menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }


    /**
     * Called whe one item in the menu is clicked
     *
     * @param item The item of the menu that was clicked
     * @return if the event was not handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        ExerciseType exerciseType = ExerciseIconsHelper.getTypeOfExercise(item);

        if (exerciseType == null) {
            return super.onOptionsItemSelected(item);
        } else {
            SelectImagesExerciseActivity.startSelectImagesExerciseActivity(getContext(), getLanguage(), exerciseType);
            return true;
        }
    }


    /**
     * @return The language that the user selected to learn
     */
    private String getLanguage() {
        if (getActivity() instanceof ILanguageActivity) {
            return ((ILanguageActivity) getActivity()).getLanguagePrefix();
        } else {
            return null;
        }
    }
}
