package com.dferreira.numbers_teach.select_images_exercise

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler
import com.dferreira.numbers_teach.generic_exercise.ExerciseMsgType
import com.dferreira.numbers_teach.generic_exercise.IImagesPalette
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice

/**
 * Handles the calls of the images selector service
 */
class SelectImagesExerciseBroadcastReceiver(
    private val activity: Activity,
    private val imagesPalette: IImagesPalette,
    private val selectedChoice: ISelectedChoice,
    private val labeledView: ILabeledHandler
) : BroadcastReceiver(), View.OnClickListener {

    /**
     * Set in the UI the label corresponding to the right option
     *
     * @param label Label to set in the UI corresponding to the right option
     */
    private fun setLabelView(label: String) {
        labeledView.setLabel(label)
    }

    /**
     * Cleans the selected view between slides
     */
    private fun resetViews() {
        selectedChoice.resetViews()
    }

    /**
     * @param imagesPath Path to the images to show in the palette
     * @param tags       Tags of of the images to show in the palette
     */
    private fun setPaletteOfOptions(imagesPath: Array<String>?, tags: Array<Any>) {
        if (imagesPath.isNullOrEmpty()) {
            return
        }
        imagesPalette.setDrawablesPaths(imagesPath)
        imagesPalette.setTags(tags)
    }

    /**
     * +
     * Called when something is to be send to the broadcast receivers
     *
     * @param context Context where the notification was trigger
     * @param intent  intent with information of the trigger method
     */
    @Synchronized
    override fun onReceive(context: Context, intent: Intent) {
        intent.extras ?: return

        val exerciseMsgType =
            intent.getSerializableExtra(SelectImagesExerciseService.TYPE_KEY) as ExerciseMsgType
        when (exerciseMsgType) {
            ExerciseMsgType.UPDATE_LIST_OF_CHOICES -> {
                updateListOfChoices(intent)

            }
            ExerciseMsgType.SHOW_RESULT_OF_CHOICE -> {
                val correct =
                    intent.getBooleanExtra(SelectImagesExerciseService.CORRECT_KEY, false)
                if (correct) {
                    selectedChoice.showCorrectChoice()
                } else {
                    selectedChoice.showWrongChoice()
                }
            }
            ExerciseMsgType.SHOW_SCORE -> {
                val finalScore = intent.getStringExtra(SelectImagesExerciseService.SCORE_KEY)
                Toast.makeText(context, finalScore, Toast.LENGTH_LONG).show()
            }
            ExerciseMsgType.FINISHING_ACTIVITY -> activity.onBackPressed()
        }
    }

    private fun updateListOfChoices(intent: Intent) {
        val label = intent.getStringExtra(SelectImagesExerciseService.LABEL_KEY)
        val images2DPath =
            intent.getStringArrayExtra(SelectImagesExerciseService.IMAGES2D_KEY)
        val tags =
            intent.getSerializableExtra(SelectImagesExerciseService.TAGS_KEY) as Array<Any>
        if (!label.isNullOrEmpty()) {
            setLabelView(label)
        }
        resetViews()
        setPaletteOfOptions(images2DPath, tags)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {}
}