package com.dferreira.numbers_teach.languages_dashboard.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.languages_dashboard.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class LanguagesDashboardFragment extends Fragment implements View.OnClickListener {

    /*Key of the language parameter passed to the activity*/
    @SuppressWarnings("WeakerAccess")
    public final static String LANGUAGE_KEY = "language";

    public static Class<?> teachingActivity;

    private ArrayList<Button> countriesButtons;

    /**
     * Called when the view is created
     *
     * @param inflater           Inflater of the layout of the activity
     * @param container          container where should be inflated
     * @param savedInstanceState bundle os the savedInstance state
     * @return the view to be inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }


    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.countriesButtons = new ArrayList<>();
        this.countriesButtons.add((Button) getActivity().findViewById(R.id.english_btn));
        this.countriesButtons.add((Button) getActivity().findViewById(R.id.french_btn));
        this.countriesButtons.add((Button) getActivity().findViewById(R.id.luxembourgish_btn));
        this.countriesButtons.add((Button) getActivity().findViewById(R.id.portuguese_btn));
        this.countriesButtons.add((Button) getActivity().findViewById(R.id.german_btn));
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        for (int i = 0; i < this.countriesButtons.size(); i++) {
            this.countriesButtons.get(i).setOnClickListener(this);
        }
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.bindViews();
        this.setEvents();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String languagePrefix = (String) v.getTag();
        Intent intent = new Intent(getContext(), teachingActivity);
        intent.putExtra(LANGUAGE_KEY, languagePrefix);

        UIHelper.startNewActivity(getActivity(), v, intent);
    }
}
