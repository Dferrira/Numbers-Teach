package com.dferreira.numbers_teach.select_images_exercise;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity;
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.generic_exercise.IImagesPalette;
import com.dferreira.numbers_teach.generic_exercise.ISelectedChoice;

import java.util.Date;

/**
 * Fragment to allow to select the right image to the user
 */
public class SelectImagesExerciseFragment extends Fragment {

    /*Receiver that is going to handle the messages send by the service*/
    private SelectImagesExerciseBroadcastReceiver receiver;


    /*UI Variables*/
    private ISelectedChoice selectedChoice;
    private ILabeledHandler labelView;
    private IImagesPalette imagesPalette;

    /**
     * Starts the service to play the audios
     *
     * @param activity     This Activity it self
     * @param language     languages chosen by the user
     * @param exerciseType Type of exercise
     */
    private void startAudioService(Activity activity, String language, ExerciseType exerciseType) {
        if (!SelectImagesExerciseService.isRunning()) {
            Intent intent = new Intent(activity, SelectImagesExerciseService.class);
            intent.putExtra(SelectImagesExerciseService.ACTIVITY_NAME, activity.getClass().getName());
            intent.putExtra(SelectImagesExerciseService.LANGUAGE, language);
            intent.putExtra(SelectImagesExerciseService.TYPE_OF_EXERCISE, exerciseType);
            activity.startService(intent);
        }
    }

    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return view of images inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.select_images_exercise_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.selectedChoice = (ISelectedChoice) getChildFragmentManager().findFragmentById(R.id.audio_description_img_frag);
        this.labelView = (ILabeledHandler) getChildFragmentManager().findFragmentById(R.id.audio_description_img_frag);
        this.imagesPalette = (IImagesPalette) getChildFragmentManager().findFragmentById(R.id.images_palette_fragment);
    }


    /**
     * Set the the on click listener to be the fragment
     */
    private void setEvents() {
        this.imagesPalette.setTarget(selectedChoice);
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

        if (getActivity() instanceof IExerciseActivity) {
            String language = ((IExerciseActivity) getActivity()).getLanguagePrefix();
            ExerciseType exerciseType = ((IExerciseActivity) getActivity()).getExerciseType();
            startAudioService(getActivity(), language, exerciseType);
            this.receiver = new SelectImagesExerciseBroadcastReceiver(getActivity(), this.imagesPalette, this.selectedChoice, this.labelView);
            getActivity().registerReceiver(receiver, new IntentFilter(SelectImagesExerciseService.NOTIFICATION));
            SelectImagesExerciseService.setActivityPaused(null);
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
        SelectImagesExerciseService.setActivityPaused((new Date().getTime()));
    }
}
