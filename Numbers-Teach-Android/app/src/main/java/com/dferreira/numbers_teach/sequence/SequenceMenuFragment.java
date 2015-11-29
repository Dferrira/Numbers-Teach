package com.dferreira.numbers_teach.sequence;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dferreira.numbers_teach.R;

/**
 * Handles the press of buttons in the
 */
public class SequenceMenuFragment extends Fragment implements
        View.OnClickListener, ILabeledView {
    private TextView imageCounter;
    private ImageButton againBtn, backBtn, playAndPauseBtn, nextBtn;


    private final String TAG = "SequenceMenuFragment";

    /*Delegator that is going to treat the click in the buttons that control the play sequence*/
    private SequenceMenuDelegator menuDelegator;


    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of the 3D models inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.sequence_menu_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
                /* Get all view controllers */
        this.imageCounter = (TextView) getActivity().findViewById(R.id.imageCounter);
        this.againBtn = (ImageButton) getActivity().findViewById(R.id.againBtn);
        this.backBtn = (ImageButton) getActivity().findViewById(R.id.backBtn);
        this.playAndPauseBtn = (ImageButton) getActivity().findViewById(R.id.playAndPauseBtn);
        this.nextBtn = (ImageButton) getActivity().findViewById(R.id.nextBtn);
    }

    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.againBtn.setOnClickListener(this);
        this.backBtn.setOnClickListener(this);
        this.playAndPauseBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);

        ILabeledActivity labeledActivity = (ILabeledActivity)getActivity().getSupportFragmentManager().findFragmentById(R.id.sequence_fragment);
        labeledActivity.setILabeledView(this);
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
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();

        this.menuDelegator = new SequenceMenuDelegator();
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
            case R.id.playAndPauseBtn: {
                menuDelegator.playAndPause();
                break;
            }
            case R.id.nextBtn: {
                menuDelegator.forward();
                break;
            }
            default:
                Log.d(TAG, "Click of button not handled");
                break;
        }
    }

    @Override
    public void setLabel(String label) {
        if(imageCounter != null) {
            this.imageCounter.setText(label);
        }
    }

}
