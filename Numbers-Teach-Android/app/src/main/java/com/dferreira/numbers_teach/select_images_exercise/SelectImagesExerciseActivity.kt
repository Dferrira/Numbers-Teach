package com.dferreira.numbers_teach.select_images_exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity
import java.io.Serializable

/**
 * Activity to pick the right image giving the sound and description
 */
class SelectImagesExerciseActivity : FragmentActivity(), IExerciseActivity {
    private lateinit var language: String

    /**
     * Return the language selected by the user know by the activity
     */
    /*Language that was selected by the user to learn*/
    override fun languagePrefix(): String = language

    /**
     * @return The reference to the type of exercise
     */
    /*If should play audio or not*/ /*If should show text or not*/
    override fun exerciseType(): ExerciseType = ExerciseType.SelectImages

    /**
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        language = intent.getStringExtra(LANGUAGE_KEY)!!
        this.setContentView(R.layout.select_images_exercise_activity)
    }

    /**
     * Method that will be called when the user presses back
     * Will inform the service that should terminate itself in order to free resources
     */
    override fun onBackPressed() {
        super.onBackPressed()
        SelectImagesExerciseService.disableToRestoreState()
    }

    companion object {
        /*Key of the language parameter passed to the activity*/
        private const val LANGUAGE_KEY = "language"
        private const val EXERCISE_TYPE = "exercise_type"

        /**
         * Start the activity activity to select the correct picture
         *
         * @param context      Context where the method is called
         * @param language     The language that was selected
         * @param exerciseType Type of exercise with different flags associated
         */
        fun startSelectImagesExerciseActivity(
            context: Context,
            language: String,
            exerciseType: ExerciseType
        ) {
            val intent = Intent(context, SelectImagesExerciseActivity::class.java)
            intent.putExtra(LANGUAGE_KEY, language)
            intent.putExtra(EXERCISE_TYPE, exerciseType as Serializable?)
            context.startActivity(intent)
        }
    }
}