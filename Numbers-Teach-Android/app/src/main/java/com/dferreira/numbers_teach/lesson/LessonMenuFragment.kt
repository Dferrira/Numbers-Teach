package com.dferreira.numbers_teach.lesson

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.generic.ui.ILabeledFragment
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler
import com.dferreira.numbers_teach.generic.ui.IPlayFragment
import com.dferreira.numbers_teach.generic.ui.IPlayHandler
import com.dferreira.numbers_teach.helpers.ErrorString

/**
 * Handles the press of buttons in the
 */
class LessonMenuFragment : Fragment(), View.OnClickListener,
    ILabeledHandler, IPlayHandler {
    private val TAG = "LessonMenuFragment"
    private lateinit var imageCounter: TextView
    private lateinit var againBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var playBtn: ImageButton
    private lateinit var pauseBtn: ImageButton
    private lateinit var nextBtn: ImageButton

    private lateinit var labeledFragment: ILabeledFragment
    private lateinit var playFragment: IPlayFragment

    /*Delegator that is going to treat the click in the buttons that control the play sequence*/
    private var menuDelegator: LessonMenuDelegator? = null

    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of the 3D model inflated
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.lesson_menu_fragment, container, false)
    }

    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews(view: View) {
        /* Get all view controllers */
        imageCounter = view.findViewById(R.id.imageCounter)
        againBtn = view.findViewById(R.id.againBtn)
        backBtn = view.findViewById(R.id.backBtn)
        playBtn = view.findViewById(R.id.playBtn)
        pauseBtn = view.findViewById(R.id.pauseBtn)
        nextBtn = view.findViewById(R.id.nextBtn)

        val parentFragment = requireParentFragment()

        labeledFragment = parentFragment as ILabeledFragment
        playFragment = parentFragment as IPlayFragment
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private fun setEvents() {
        againBtn.setOnClickListener(this)
        backBtn.setOnClickListener(this)
        playBtn.setOnClickListener(this)
        pauseBtn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
        labeledFragment.setILabeledView(this)
        playFragment.setIPlayView(this)
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
     * Show or hide the visibility of the play/pause buttons
     */
    override fun updatePlayPauseButtons() {
        if (LessonService.getInstance() == null) {
            playBtn.visibility = View.GONE
            pauseBtn.visibility = View.VISIBLE
            return
        }
        if (LessonService.getInstance().isPlaying) {
            playBtn.visibility = View.GONE
            pauseBtn.visibility = View.VISIBLE
        } else {
            playBtn.visibility = View.VISIBLE
            pauseBtn.visibility = View.GONE
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to { Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()
        menuDelegator = LessonMenuDelegator()
        updatePlayPauseButtons()
    }

    override fun onPause() {
        super.onPause()
        menuDelegator = null
    }

    /**
     * Directs the click at one from a button to a specific method on
     * introduction delegate
     */
    override fun onClick(v: View) {
        if (menuDelegator == null) {
            return
        }
        when (v.id) {
            R.id.againBtn -> {
                menuDelegator!!.reload()
            }
            R.id.backBtn -> {

                // Back
                menuDelegator!!.previous()
            }
            R.id.playBtn, R.id.pauseBtn -> {
                menuDelegator!!.playOrPause()
                updatePlayPauseButtons()
            }
            R.id.nextBtn -> {
                menuDelegator!!.forward()
            }
            else -> Log.d(TAG, ErrorString.CLICK_OF_BUTTON_NOT_HANDLED)
        }
    }

    /**
     * @param label Set the label of the view that is going to be show to the user
     */
    override fun setLabel(label: String) {
        imageCounter.text = label
    }
}