package com.dferreira.numbers_teach.scores

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
class ExerciseScoresListFragment : Fragment() {
    private var exerciseScoresListAdapter: ExerciseScoresListAdapter? = null

    /*Reference to the UI list that show the list of score in the exercise*/
    private lateinit var listOfExerciseScores: ListView

    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of fragment of the list of activities available to the user
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.exercise_scores_list_fragment, container, false)
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews(view: View) {
        listOfExerciseScores =
            view.findViewById(R.id.list_of_exercise_scores)
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private fun setEvents() {
        val currentContext = requireContext()
        exerciseScoresListAdapter = ExerciseScoresListAdapter(currentContext)
        listOfExerciseScores.adapter = exerciseScoresListAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setEvents()
        updateListOfScores()
    }

    /**
     * Get exercise selected in the activity and use it to update the list of results
     */
    fun updateListOfScores() {
        if (activity is IScoreExerciseActivity) {
            val exerciseActivity = activity as IScoreExerciseActivity?
            val languagePrefix = exerciseActivity!!.languagePrefix()
            val exerciseActivityName = exerciseActivity.exerciseActivityName()
            if (TextUtils.isEmpty(exerciseActivityName)) {
                return
            }
            exerciseScoresListAdapter!!.updateListOfItems(
                languagePrefix,
                exerciseActivityName,
                exerciseActivity.exerciseType()
            )
        }
    }
}