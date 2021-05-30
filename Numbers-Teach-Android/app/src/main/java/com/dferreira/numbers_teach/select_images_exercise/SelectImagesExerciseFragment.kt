package com.dferreira.numbers_teach.select_images_exercise

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler
import com.dferreira.numbers_teach.generic_exercise.IImagesPalette
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice
import java.util.*

/**
 * Fragment to allow to select the right image to the user
 */
class SelectImagesExerciseFragment : Fragment() {
    /*Receiver that is going to handle the messages send by the service*/
    private lateinit var receiver: BroadcastReceiver

    /*UI Variables*/
    private lateinit var selectedChoice: ISelectedChoice
    private lateinit var labelView: ILabeledHandler
    private lateinit var imagesPalette: IImagesPalette



    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of images inflated
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.select_images_exercise_fragment, container, false)
    }



    /**
     * Set the the on click listener to be the fragment
     */


    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setEvents()
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews() {
        selectedChoice = childFragmentManager.findFragmentById(R.id.audio_description_img_frag) as ISelectedChoice
        labelView =
            childFragmentManager.findFragmentById(R.id.audio_description_img_frag) as ILabeledHandler
        imagesPalette =
            childFragmentManager.findFragmentById(R.id.images_palette_fragment) as IImagesPalette
    }

    private fun setEvents() {
        imagesPalette.setTarget(selectedChoice)
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        val exerciseActivity = (activity as IExerciseActivity)
        val language = exerciseActivity.languagePrefix()
        val exerciseType = exerciseActivity.exerciseType()
        startAudioService(activity, language, exerciseType)

        receiver = createReceiver(activity)

        activity.registerReceiver(
            receiver,
            IntentFilter(SelectImagesExerciseService.NOTIFICATION)
        )
        SelectImagesExerciseService.setActivityPaused(null)
    }

    /**
     * Starts the service to play the audios
     *
     * @param activity     This Activity it self
     * @param language     languages chosen by the user
     * @param exerciseType Type of exercise
     */
    private fun startAudioService(
        activity: Activity,
        language: String,
        exerciseType: ExerciseType
    ) {
        if (SelectImagesExerciseService.isRunning()) {
            return
        }
        val intent = Intent(activity, SelectImagesExerciseService::class.java)
        intent.putExtra(SelectImagesExerciseService.ACTIVITY_NAME, activity.javaClass.name)
        intent.putExtra(SelectImagesExerciseService.LANGUAGE, language)
        intent.putExtra(SelectImagesExerciseService.TYPE_OF_EXERCISE, exerciseType)
        activity.startService(intent)
    }

    private fun createReceiver(activity: Activity): BroadcastReceiver {
        return SelectImagesExerciseBroadcastReceiver(
            activity,
            imagesPalette,
            selectedChoice,
            labelView
        )
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
        SelectImagesExerciseService.setActivityPaused(Date().time)
    }
}