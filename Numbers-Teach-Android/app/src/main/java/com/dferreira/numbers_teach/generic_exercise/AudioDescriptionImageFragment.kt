package com.dferreira.numbers_teach.generic_exercise

import android.annotation.TargetApi
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.drag_and_drop.DropAction
import com.dferreira.numbers_teach.drag_and_drop.DroppableListener
import com.dferreira.numbers_teach.exercise_icons.models.exercise_type_description.ExerciseTypeDescriptionFactory
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler
import com.dferreira.numbers_teach.helpers.ErrorString
import com.dferreira.numbers_teach.helpers.ImageHelper

/**
 * Fragment that shows on top
 * Audio possibility and description
 * On Bottom
 */
class AudioDescriptionImageFragment : Fragment(),
    ISelectedChoice, ILabeledHandler {
    private val TAG = "ADescriptionImageF"

    private lateinit var audioDescriptionTv: TextView

    /*Image view that is going to get the images selected by the user*/
    private lateinit var selectedImage: ImageView

    /*Image view that indicates that the user have made the correct choice*/
    private lateinit var correctImage: ImageView

    /*Image view that indicates the the user have made the wrong choice*/
    private lateinit var wrongImage: ImageView
    private lateinit var fragment: View

    private var whiteShape: Drawable? = null
    private var redShape: Drawable? = null

    /*Indicates if should or not play audio to the user*/
    private var withAudio = false

    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return the image when can drag the content
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.audio_description_image_fragment, container, false)
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews(view: View) {
        audioDescriptionTv = view.findViewById(R.id.audio_description_tv)
        selectedImage = view.findViewById(R.id.selected_image)
        correctImage = view.findViewById(R.id.correct_image_view)
        wrongImage = view.findViewById(R.id.wrong_image_view)
        fragment = view
    }

    /**
     * Hide some elements of the UI depending of the exercise request by the the user
     */
    private fun hideUIViews() {
        if (activity is IExerciseActivity) {
            val activity = activity as IExerciseActivity?
            val exerciseType = activity!!.exerciseType
            val exerciseDescriptionFactory = ExerciseTypeDescriptionFactory()
            val (_, isWithAudio, withText) = exerciseDescriptionFactory.createDescription(
                exerciseType
            )
            withAudio = isWithAudio
            /*Indicates if should or not show the label to the user*/
            if (!withAudio) {
                audioDescriptionTv.setCompoundDrawables(null, null, null, null)
            }
            if (!withText) {
                audioDescriptionTv.text = ""
            }
        }
    }

    /**
     * Initialize the variables that will support the render of the fragment
     *
     * @param savedInstanceState if not null if going to use the bundle to restore the label
     */
    private fun loadData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val label = savedInstanceState.getString(LABEL_KEY)
            if (!TextUtils.isEmpty(label)) {
                audioDescriptionTv.text = label
            }
        }
    }

    /**
     * Handle the drop of views in the AudioDescription Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun setHoneycombEvents() {
        val target: ISelectedChoice = this
        val dropAction = DropAction.MOVE_CHANGE_BACKGROUND
        val droppableListener = DroppableListener(
            target,
            dropAction
        )
        audioDescriptionTv.setOnDragListener(droppableListener)
        selectedImage.setOnDragListener(droppableListener)
        fragment.setOnDragListener(droppableListener)
    }

    /**
     * Set the event that are going handle the actions of the user
     */
    private fun setEvents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setHoneycombEvents()
        }

        //If the user clicks in description gets the audio again
        if (withAudio) {
            audioDescriptionTv.setOnClickListener {
                UserActionMsgProvider.getInstance().triggerReplay()
            }
        }
    }

    /**
     * Load the shapes that are going to be used when the user drag objects
     */
    private fun loadShapes() {
        whiteShape = ImageHelper.getDrawable(context, R.drawable.shape_white_drop_target)
        redShape = ImageHelper.getDrawable(context, R.drawable.shape_red_drop_target)
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        hideUIViews()
        loadData(savedInstanceState)
        setEvents()
        loadShapes()
    }

    /**
     * Save the state of the instance
     *
     * @param stateOut Bundle that is going to keep the state of the palette of images
     */
    override fun onSaveInstanceState(stateOut: Bundle) {
        super.onSaveInstanceState(stateOut)
        stateOut.putString(LABEL_KEY, audioDescriptionTv.text.toString())
    }

    /**
     * Is going to verify if the view that was selected matches the expected  image
     *
     * @param selectedView to check if matches
     */
    override fun notifyViewSelected(selectedView: View?) {
        if (selectedView == null) {
            Log.d(TAG, ErrorString.SOMETHING_GOES_WRONG)
        } else {
            val selectedDrawable = ImageHelper.getDrawable(selectedView)
            val selectedLayer = ImageHelper.createCardBackground(
                context, selectedDrawable
            )
            if (selectedDrawable == null) {
                Log.d(TAG, ErrorString.SOMETHING_GOES_WRONG)
            } else {
                ImageHelper.setBackgroundDrawable(selectedImage, selectedLayer)
                UserActionMsgProvider.getInstance().triggerOptionSelected(selectedView.tag)
            }
        }
    }

    /**
     * Set the background of the droppable area to notice that something has entered there
     */
    override fun notifyEnteredInDroppableArea() {
        ImageHelper.setBackgroundDrawable(selectedImage, redShape)
        correctImage.visibility = View.INVISIBLE
        wrongImage.visibility = View.INVISIBLE
    }

    /**
     * Indicate that the dragged element have leaved the droppable area
     */
    override fun notifyLeaveDroppableArea() {
        ImageHelper.setBackgroundDrawable(selectedImage, whiteShape)
        correctImage.visibility = View.INVISIBLE
        wrongImage.visibility = View.INVISIBLE
    }

    /**
     * Set the text to show to the user
     *
     * @param label text to put in the label
     */
    override fun setLabel(label: String) {
        audioDescriptionTv.text = label
    }

    /**
     * Show the user that was made the correct choice
     */
    override fun showCorrectChoice() {
        correctImage.visibility = View.VISIBLE
        wrongImage.visibility = View.INVISIBLE
    }

    /**
     * Show the user that was made the wrong choice
     */
    override fun showWrongChoice() {
        correctImage.visibility = View.INVISIBLE
        wrongImage.visibility = View.VISIBLE
    }

    /**
     * Ideal to reset any view that needs to be clean between passages
     */
    override fun resetViews() {
        ImageHelper.setBackgroundDrawable(selectedImage, whiteShape)
        correctImage.visibility = View.INVISIBLE
        wrongImage.visibility = View.INVISIBLE
    }

    companion object {
        private const val LABEL_KEY = "Label"
    }
}