package com.dferreira.numbers_teach.scores;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity;

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
public class ExerciseScoresListFragment extends Fragment {
    private ExerciseScoresListAdapter exerciseScoresListAdapter;

    /*Reference to the UI list that show the list of score in the exercise*/
    private ListView listOfExerciseScores;


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
        return inflater.inflate(R.layout.exercise_scores_list_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.listOfExerciseScores = (ListView) getActivity().findViewById(R.id.list_of_exercise_scores);
    }


    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.exerciseScoresListAdapter = new ExerciseScoresListAdapter(getContext());
        this.listOfExerciseScores.setAdapter(exerciseScoresListAdapter);
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.bindViews();
        this.setEvents();
        this.updateListOfScores();
    }

    /**
     * Get exercise selected in the activity and use it to update the list of results
     */
    public void updateListOfScores() {
        if (getActivity() instanceof IScoreExerciseActivity) {
            IScoreExerciseActivity exerciseActivity = (IScoreExerciseActivity) getActivity();
            String languagePrefix = exerciseActivity.getLanguagePrefix();
            String exerciseActivityName = exerciseActivity.getExerciseActivityName();

            if (TextUtils.isEmpty(exerciseActivityName)) {
                return;
            }


            this.exerciseScoresListAdapter.updateListOfItems(languagePrefix, exerciseActivityName, exerciseActivity.getExerciseType());
        }
    }
}
