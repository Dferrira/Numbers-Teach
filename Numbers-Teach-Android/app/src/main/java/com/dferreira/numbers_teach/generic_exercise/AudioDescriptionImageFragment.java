package com.dferreira.numbers_teach.generic_exercise;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.drag_and_drop.DropAction;
import com.dferreira.numbers_teach.drag_and_drop.DroppableListener;
import com.dferreira.numbers_teach.generic.ui.IExerciseActivity;
import com.dferreira.numbers_teach.generic.ui.ILabeledHandler;
import com.dferreira.numbers_teach.helpers.ErrorString;
import com.dferreira.numbers_teach.helpers.ImageHelper;


/**
 * Fragment that shows on top
 * Audio possibility and description
 * On Bottom
 */
public class AudioDescriptionImageFragment extends Fragment implements ISelectedChoice, ILabeledHandler {


    private static final String LABEL_KEY = "Label";
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "ADescriptionImageF";
    private TextView audioDescriptionTv;

    /*Image view that is going to get the images selected by the user*/
    private ImageView selectedImage;
    /*Image view that indicates that the user have made the correct choice*/
    private ImageView correctImage;
    /*Image view that indicates the the user have made the wrong choice*/
    private ImageView wrongImage;

    private View fragment;

    private Drawable whiteShape;
    private Drawable redShape;

    /*Indicates if should or not play audio to the user*/
    private boolean withAudio;


    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return the image when can drag the content
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.audio_description_image_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        /* Get all view controllers */
        this.audioDescriptionTv = (TextView) getActivity().findViewById(R.id.audio_description_tv);
        this.selectedImage = (ImageView) getActivity().findViewById(R.id.selected_image);
        this.correctImage = (ImageView) getActivity().findViewById(R.id.correct_image_view);
        this.wrongImage = (ImageView) getActivity().findViewById(R.id.wrong_image_view);
        this.fragment = getActivity().findViewById(R.id.audio_description_img_frag);
    }

    /**
     * Hide some elements of the UI depending of the exercise request by the the user
     */
    private void hideUIViews() {
        if (getActivity() instanceof IExerciseActivity) {
            IExerciseActivity activity = (IExerciseActivity) getActivity();
            this.withAudio = activity.getExerciseType().isWithAudio();
            /*Indicates if should or not show the label to the user*/
            boolean withText = activity.getExerciseType().isWithText();
            if (!this.withAudio) {
                this.audioDescriptionTv.setCompoundDrawables(null, null, null, null);
            }
            if (!withText) {
                this.audioDescriptionTv.setText("");
            }
        }
    }

    /**
     * Initialize the variables that will support the render of the fragment
     *
     * @param savedInstanceState if not null if going to use the bundle to restore the label
     */
    private void loadData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String label = savedInstanceState.getString(LABEL_KEY);
            if (!TextUtils.isEmpty(label)) {
                this.audioDescriptionTv.setText(label);
            }
        }
    }


    /**
     * Handle the drop of views in the AudioDescription Fragment
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setHoneycombEvents() {
        DroppableListener droppableListener = new DroppableListener();
        droppableListener.setDropAction(DropAction.MOVE_CHANGE_BACKGROUND);
        droppableListener.setTarget(this);

        this.audioDescriptionTv.setOnDragListener(droppableListener);
        this.selectedImage.setOnDragListener(droppableListener);
        this.fragment.setOnDragListener(droppableListener);
    }

    /**
     * Set the event that are going handle the actions of the user
     */
    private void setEvents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setHoneycombEvents();
        }

        //If the user clicks in description gets the audio again
        if (withAudio) {
            this.audioDescriptionTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserActionMsgProvider.getInstance().triggerReplay();
                }
            });
        }
    }

    /**
     * Load the shapes that are going to be used when the user drag objects
     */
    private void loadShapes() {
        this.whiteShape = ImageHelper.getDrawable(getContext(), R.drawable.shape_white_drop_target);
        this.redShape = ImageHelper.getDrawable(getContext(), R.drawable.shape_red_drop_target);
    }


    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.bindViews();
        this.hideUIViews();
        this.loadData(savedInstanceState);
        this.setEvents();
        this.loadShapes();
    }

    /**
     * Save the state of the instance
     *
     * @param stateOut Bundle that is going to keep the state of the palette of images
     */
    public void onSaveInstanceState(Bundle stateOut) {
        super.onSaveInstanceState(stateOut);

        stateOut.putString(LABEL_KEY, this.audioDescriptionTv.getText().toString());
    }

    /**
     * Is going to verify if the view that was selected matches the expected  image
     *
     * @param selectedView to check if matches
     */
    @Override
    public void triggerViewSelected(View selectedView) {
        if (selectedView == null) {
            Log.d(TAG, ErrorString.SOMETHING_GOES_WRONG);
        } else {
            Drawable selectedDrawable = ImageHelper.getDrawable(selectedView);
            LayerDrawable selectedLayer = ImageHelper.getCardBackground(getContext(), selectedDrawable);
            if (selectedDrawable == null) {
                Log.d(TAG, ErrorString.SOMETHING_GOES_WRONG);
            } else {
                ImageHelper.setBackgroundDrawable(selectedImage, selectedLayer);
                UserActionMsgProvider.getInstance().triggerOptionSelected(selectedView.getTag());
            }
        }
    }

    /**
     * Set the background of the droppable area to notice that something has entered there
     */
    @Override
    public void setEnteredInDroppableArea() {
        ImageHelper.setBackgroundDrawable(selectedImage, redShape);
        this.correctImage.setVisibility(View.INVISIBLE);
        this.wrongImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Indicate that the dragged element have leaved the droppable area
     */
    @Override
    public void setLeaveDroppableArea() {
        ImageHelper.setBackgroundDrawable(selectedImage, whiteShape);
        this.correctImage.setVisibility(View.INVISIBLE);
        this.wrongImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Set the text to show to the user
     *
     * @param label text to put in the label
     */
    @Override
    public void setLabel(String label) {
        this.audioDescriptionTv.setText(label);
    }

    /**
     * Show the user that was made the correct choice
     */
    @Override
    public void showCorrectChoice() {
        this.correctImage.setVisibility(View.VISIBLE);
        this.wrongImage.setVisibility(View.INVISIBLE);
    }

    /**
     * Show the user that was made the wrong choice
     */
    @Override
    public void showWrongChoice() {
        this.correctImage.setVisibility(View.INVISIBLE);
        this.wrongImage.setVisibility(View.VISIBLE);
    }

    /**
     * Ideal to reset any view that needs to be clean between passages
     */
    @Override
    public void resetViews() {
        ImageHelper.setBackgroundDrawable(selectedImage, whiteShape);
        this.correctImage.setVisibility(View.INVISIBLE);
        this.wrongImage.setVisibility(View.INVISIBLE);
    }

}
