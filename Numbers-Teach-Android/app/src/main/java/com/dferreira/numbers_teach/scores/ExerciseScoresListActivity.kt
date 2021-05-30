package com.dferreira.numbers_teach.scores

import android.os.Bundle
import android.view.View
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.commons.GenericActivity
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon.ExerciseTypeIconFactory
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity

/**
 * Show the list of scores that the user got in a certain exercise
 */
class ExerciseScoresListActivity : GenericActivity(), IScoreExerciseActivity {

    private lateinit var language: String

    private var currentExerciseType: ExerciseType? = null

    /*String with name of the activity of the exercise selected*/
    private var exerciseActivitySelected: String? = null

    /**
     * @return the type of exercise selected by the user to see its previous score
     */
    /*If the right picture was show to the user or not*/ /*If the right audio was play to the user or not*/ /*If the right text was show to the user or not*/


    /**
     * Get the information of the intent and set it in the local variables of the
     * of the activity
     */
    private fun setLocalExerciseVariables() {
        val intent = intent
        language = intent.getStringExtra(LANGUAGE_KEY)!!
        exerciseActivitySelected = intent.getStringExtra(EXERCISE_ACTIVITY_KEY)
        currentExerciseType = intent.getSerializableExtra(EXERCISE_TYPE_KEY) as ExerciseType?
    }

    /**
     * Depending which was the exercise selected will show the matching icon
     */
    private fun setIconTitle() {
        val exerciseType = currentExerciseType ?: return
        val exerciseTypeIconFactory = ExerciseTypeIconFactory()
        val icon = exerciseTypeIconFactory.createIcon(exerciseType)
        val iconId = icon.exerciseIcon
        setupActionBar(iconId)
    }

    /**
     * When created is going inflate the layout
     *
     * @param savedInstanceState last known state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocalExerciseVariables()
        this.setContentView(R.layout.exercise_scores_list_activity)
        setIconTitle()
    }

    override fun exerciseType(): ExerciseType = currentExerciseType!!

    override fun languagePrefix(): String = language

    /**
     * @return The complete name of the activity where were got the results
     */
    override fun exerciseActivityName(): String? {
        return exerciseActivitySelected
    }

    /**
     * Set the properties of the exercise selected by the user
     *
     * @param exerciseActivitySelected of the activity of the exercise
     * @param exerciseType             the flag to if the exercise was showing (picture,audio,text) or not
     * @param triggerView              View that trigger the event
     */
    override fun setExerciseProperties(
        exerciseActivitySelected: String?,
        exerciseType: ExerciseType?,
        triggerView: View
    ) {
        this.exerciseActivitySelected = exerciseActivitySelected
        this.currentExerciseType = exerciseType
    }

    companion object {
        /*Key of the language parameter passed to the activity*/
        const val LANGUAGE_KEY = "language"
        const val EXERCISE_ACTIVITY_KEY = "exercise_activity"
        const val EXERCISE_TYPE_KEY = "exercise_type"
    }
}