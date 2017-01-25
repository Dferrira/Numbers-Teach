package com.dferreira.numbers_teach.lesson;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.generic.gestures.SwipeDetector;
import com.dferreira.numbers_teach.generic.ui.ILabeledFragment;
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.generic.ui.ILanguageActivity;
import com.dferreira.numbers_teach.generic.ui.IPlayFragment;
import com.dferreira.numbers_teach.generic.ui.IPlayHandler;

import java.util.Date;

import com.dferreira.numbers_teach.languages_dashboard.views.UIHelper;

/**
 * Fragment with a lesson_fragment of 3D objects to show
 */
public class LessonFragment extends Fragment implements
        View.OnClickListener, ILabeledFragment, IPlayFragment {


    /*UI elements*/
    private ViewFlipper viewFlipper;
    private View playerPanel;
    private ILabeledHandler sequenceFragmentHandler;
    private IPlayHandler playHandler;
    private TextView imageDescription1;
    private TextView imageDescription2;


    /*Receiver that is going to handle the messages send by the service*/
    private LessonBroadcastReceiver receiver;

    private GestureDetector gestureDetector;

    /**
     * Starts the service to play the audios
     *
     * @param context This Activity it self
     */
    private void startAudioService(Context context, String language) {
        if (!LessonService.isRunning()) {
            Intent intent = new Intent(context, LessonService.class);
            intent.putExtra(LessonService.LANGUAGE, language);
            context.startService(intent);
        }
    }

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

        return inflater.inflate(R.layout.lesson_fragment, container, false);
    }


    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.viewFlipper = (ViewFlipper) getActivity().findViewById(R.id.viewFlipper);

        this.playerPanel = getActivity().findViewById(R.id.playerPanel);
        LessonGesturesHandler lessonGesturesHandler = new LessonGesturesHandler(getActivity());
        this.gestureDetector = new GestureDetector(getActivity(), new SwipeDetector(lessonGesturesHandler));

        //Bind image description views
        this.imageDescription1 = (TextView) getActivity().findViewById(R.id.imageDescription1);
        this.imageDescription2 = (TextView) getActivity().findViewById(R.id.imageDescription2);
    }


    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.viewFlipper.setOnClickListener(this);
        this.imageDescription1.setOnClickListener(this);
        this.imageDescription2.setOnClickListener(this);

        this.imageDescription1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        this.imageDescription2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
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
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof ILanguageActivity) {
            String language = ((ILanguageActivity) getActivity()).getLanguagePrefix();
            startAudioService(getActivity(), language);

            this.receiver = new LessonBroadcastReceiver(getActivity(), this.viewFlipper, this.sequenceFragmentHandler, this.playHandler);
            this.receiver.setTextViews(this.imageDescription1, this.imageDescription2);
            getActivity().registerReceiver(receiver, new IntentFilter(LessonService.NOTIFICATION));
            LessonService.setActivityPaused(null);
        }
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
        LessonService.setActivityPaused((new Date().getTime()));
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewFlipper:
            case R.id.imageDescription1:
            case R.id.imageDescription2:
                UIHelper.toggleVisibilityView(playerPanel);
                break;
        }
    }

    /**
     * @param sequenceFragmentHandler Handler that is going to handle the change of the
     *                                label text value
     */
    @Override
    public void setILabeledView(ILabeledHandler sequenceFragmentHandler) {
        this.sequenceFragmentHandler = sequenceFragmentHandler;
    }

    /**
     * @param playHandler Set handler that is going to handle the update of the state of
     *                    the button play/pause
     */
    @Override
    public void setIPlayView(IPlayHandler playHandler) {
        this.playHandler = playHandler;
    }
}
