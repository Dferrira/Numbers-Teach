package com.dferreira.numbers_teach.scores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
class GlobalScoresListFragment : Fragment() {
    private lateinit var listOfScores: ListView

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
        return inflater.inflate(R.layout.global_scores_list_fragment, container, false)
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setEvents()
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews(view: View) {
        listOfScores = view.findViewById<View>(R.id.list_of_scores) as ListView
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private fun setEvents() {
        val currentActivity = requireActivity()
        val language = language()
        val globalScoresListAdapter = GlobalScoresListAdapter(currentActivity, language)
        listOfScores.adapter = globalScoresListAdapter
        listOfScores.onItemClickListener = globalScoresListAdapter
    }

    /**
     * @return The language that the user selected to learn
     */
    private fun language(): String {
        return (activity as ILanguageActivity?)!!.languagePrefix()
    }
}