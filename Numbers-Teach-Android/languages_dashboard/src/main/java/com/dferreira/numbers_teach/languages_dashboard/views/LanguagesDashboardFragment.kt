package com.dferreira.numbers_teach.languages_dashboard.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.languages_dashboard.R

/**
 * A placeholder fragment containing a simple view.
 */
class LanguagesDashboardFragment : Fragment(), View.OnClickListener {

    /**
     * Called when the view is created
     *
     * @param inflater           Inflater of the layout of the activity
     * @param container          container where should be inflated
     * @param savedInstanceState bundle os the savedInstance state
     * @return the view to be inflated
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun findButtons(view: View): List<Button> {
        var buttonIdList = getButtonIdList()
        return buttonIdList
            .map {
                buttonId -> view.findViewById(buttonId)
            }
    }

    private fun getButtonIdList(): List<Int> {
        return listOf(
            R.id.english_btn,
            R.id.french_btn,
            R.id.luxembourgish_btn,
            R.id.portuguese_btn,
            R.id.german_btn
        )
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private fun setEvents(countriesButtons: List<Button>) {
        countriesButtons.
        forEach {
                countryButton -> countryButton.setOnClickListener(this)
            }
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countriesButtons = findButtons(view)
        setEvents(countriesButtons)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        val languagePrefix = view.tag as String
        val intent = Intent(context, teachingActivity)
        intent.putExtra(LANGUAGE_KEY, languagePrefix)
        UIHelper.startNewActivity(activity, view, intent)
    }

    companion object {
        /*Key of the language parameter passed to the activity*/
        const val LANGUAGE_KEY = "language"
        var teachingActivity: Class<*>? = null
    }
}