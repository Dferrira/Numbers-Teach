package com.dferreira.numbers_teach.scores;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity;

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
public class GlobalScoresListFragment extends Fragment {
    private ListView listOfScores;


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
        return inflater.inflate(R.layout.global_scores_list_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.listOfScores = (ListView) getActivity().findViewById(R.id.list_of_scores);
    }


    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        GlobalScoresListAdapter globalScoresListAdapter = new GlobalScoresListAdapter(getActivity(), getLanguage());
        this.listOfScores.setAdapter(globalScoresListAdapter);
        this.listOfScores.setOnItemClickListener(globalScoresListAdapter);
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