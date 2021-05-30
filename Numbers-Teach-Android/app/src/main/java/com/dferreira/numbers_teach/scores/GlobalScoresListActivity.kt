package com.dferreira.numbers_teach.scores

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.commons.GenericActivity
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity
import com.dferreira.numbers_teach.languages_dashboard.views.UIHelper

/**
 * Show a list of possible activities that the user could take to learn the numbers in the
 * chosen language
 */
class GlobalScoresListActivity : GenericActivity(), IScoreExerciseActivity {
    private lateinit var language: String
    private var exerciseTypeSelected: ExerciseType? = null

    /*String with name of the activity of the exercise selected*/
    private var exerciseActivityName: String? = null


    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last known state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language = if (savedInstanceState == null) {
            intent.getStringExtra(LANGUAGE_KEY)!!
        } else {
            savedInstanceState.getString(LANGUAGE_KEY)!!
        }
        this.setContentView(R.layout.global_scores_list_activity)
        setupActionBar(R.mipmap.leaderboard)
    }

    /**
     * Called when the activity needs to get the result save for any reason
     *
     * @param savedInstanceState Bundle to get the saved state of the activity
     */
    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(
            LANGUAGE_KEY,
            language
        )
    }

    /**
     * Start an activity that show a list of scores of a specific exercise
     *
     * @param triggerView View that trigger the event
     */
    private fun startExerciseScoresListActivity(triggerView: View) {
        val intent = Intent(this, ExerciseScoresListActivity::class.java)
        intent.putExtra(ExerciseScoresListActivity.LANGUAGE_KEY, language)
        intent.putExtra(ExerciseScoresListActivity.EXERCISE_ACTIVITY_KEY, exerciseActivityName)
        intent.putExtra(ExerciseScoresListActivity.EXERCISE_TYPE_KEY, exerciseTypeSelected)
        UIHelper.startNewActivity(this, triggerView, intent)
    }

    /**
     * Set the properties of the exercise selected by the user
     *
     * @param exerciseActivitySelected of the activity of the exercise
     * @param exerciseType             Indicates if the user was seeing the correct (picture,audio, text) when got the result
     * @param triggerView              View that trigger the event
     */
    override fun setExerciseProperties(
        exerciseActivitySelected: String?,
        exerciseType: ExerciseType?,
        triggerView: View
    ) {
        exerciseActivityName = exerciseActivitySelected
        this.exerciseTypeSelected = exerciseType
        val exerciseScoreList =
            supportFragmentManager.findFragmentById(R.id.exercise_list_fragment) as ExerciseScoresListFragment?
        if (exerciseScoreList == null) {
            startExerciseScoresListActivity(triggerView)
        } else {
            exerciseScoreList.updateListOfScores()
        }
    }

    override fun exerciseType(): ExerciseType = exerciseTypeSelected!!

    override fun languagePrefix(): String = language

    /**
     * @return the name of the activity where the the score was got the score
     */
    override fun exerciseActivityName(): String? {
        return exerciseActivityName
    }

    companion object {
        /*Key of the language parameter passed to the activity*/
        const val LANGUAGE_KEY = "language"
    }
}