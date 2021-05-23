package com.dferreira.numbers_teach.lesson;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.ui.ILabeledFragment;
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.generic.ui.IPlayFragment;
import com.dferreira.numbers_teach.generic.ui.IPlayHandler;
import com.dferreira.numbers_teach.helpers.ErrorString;


/**
 * Handles the press of buttons in the
 */
public class LessonMenuFragment extends Fragment implements
        View.OnClickListener, ILabeledHandler, IPlayHandler {
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "LessonMenuFragment";
    private TextView imageCounter;
    private ImageButton againBtn, backBtn, playBtn, pauseBtn, nextBtn;
    /*Delegator that is going to treat the click in the buttons that control the play sequence*/
    private LessonMenuDelegator menuDelegator;


    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of the 3D model inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.lesson_menu_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        /* Get all view controllers */
        this.imageCounter = (TextView) getActivity().findViewById(R.id.imageCounter);
        this.againBtn = (ImageButton) getActivity().findViewById(R.id.againBtn);
        this.backBtn = (ImageButton) getActivity().findViewById(R.id.backBtn);
        this.playBtn = (ImageButton) getActivity().findViewById(R.id.playBtn);
        this.pauseBtn = (ImageButton) getActivity().findViewById(R.id.pauseBtn);
        this.nextBtn = (ImageButton) getActivity().findViewById(R.id.nextBtn);
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.againBtn.setOnClickListener(this);
        this.backBtn.setOnClickListener(this);
        this.playBtn.setOnClickListener(this);
        this.pauseBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);

        ILabeledFragment labeledFragment = (ILabeledFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.sequence_fragment);
        labeledFragment.setILabeledView(this);
        IPlayFragment playFragment = (IPlayFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.sequence_fragment);
        playFragment.setIPlayView(this);
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.bindViews();
        this.setEvents();
    }


    /**
     * Show or hide the visibility of the play/pause buttons
     */
    @Override
    public void updatePlayPauseButtons() {
        if (LessonService.getInstance() == null) {
            this.playBtn.setVisibility(View.GONE);
            this.pauseBtn.setVisibility(View.VISIBLE);
            return;
        }

        if (LessonService.getInstance().isPlaying()) {
            this.playBtn.setVisibility(View.GONE);
            this.pauseBtn.setVisibility(View.VISIBLE);
        } else {
            this.playBtn.setVisibility(View.VISIBLE);
            this.pauseBtn.setVisibility(View.GONE);
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to { Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        this.menuDelegator = new LessonMenuDelegator();

        updatePlayPauseButtons();
    }

    @Override
    public void onPause() {
        super.onPause();

        this.menuDelegator = null;
    }


    /**
     * Directs the click at one from a button to a specific method on
     * introduction delegate
     */
    @Override
    public void onClick(View v) {
        if (menuDelegator == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.againBtn: {
                menuDelegator.reload();
                break;
            }
            case R.id.backBtn: {
                // Back
                menuDelegator.previous();
                break;
            }
            case R.id.playBtn:
            case R.id.pauseBtn: {
                this.menuDelegator.playOrPause();
                updatePlayPauseButtons();
                break;
            }


            case R.id.nextBtn: {
                menuDelegator.forward();
                break;
            }
            default:
                Log.d(TAG, ErrorString.CLICK_OF_BUTTON_NOT_HANDLED);
                break;
        }
    }

    /**
     * @param label Set the label of the view that is going to be show to the user
     */
    @Override
    public void setLabel(String label) {
        if (imageCounter != null) {
            this.imageCounter.setText(label);
        }
    }
}
