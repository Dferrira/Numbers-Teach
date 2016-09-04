package com.dferreira.numbers_teach.generic_exercise;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.drag_and_drop.ClickableListener;
import com.dferreira.numbers_teach.drag_and_drop.DraggableListener;
import com.dferreira.numbers_teach.drag_and_drop.DropAction;
import com.dferreira.numbers_teach.drag_and_drop.DroppableListener;
import com.dferreira.numbers_teach.helpers.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Support a bunch of images that can be changed dynamically
 */
public class ImagesPaletteFragment extends Fragment implements IImagesPalette {

    private static final int IMAGES_SIZE = 4;

    private static final String PATHS_KEY = "PATHS";
    private static final String VISIBILITY_FLAGS_KEY = "VISIBILITY_FLAGS";
    private static final String TAGS_KEY = "TAGS";

    private ViewGroup imagesPalette;
    private List<ImageView> selectableImageList;

    /*Listener that is going to get the click of the user*/
    private ISelectedChoice target;

    /*List of paths of the images in the palette*/
    private String[] paths;

    /*List of tags that are in the palette of images*/
    private Object[] tags;


    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return the view with images for the user to pick or click
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.images_palette_fragment, container, false);
    }

    /**
     * Bind the the views to the respective variables
     */
    private void bindViews() {
        this.selectableImageList = new ArrayList<>(IMAGES_SIZE);

        /* Get the images reference */
        this.selectableImageList.add((ImageView) getActivity().findViewById(R.id.opt_img1));
        this.selectableImageList.add((ImageView) getActivity().findViewById(R.id.opt_img2));
        this.selectableImageList.add((ImageView) getActivity().findViewById(R.id.opt_img3));
        this.selectableImageList.add((ImageView) getActivity().findViewById(R.id.opt_img4));
        /*Get the layout reference */
        this.imagesPalette = (ViewGroup) getActivity().findViewById(R.id.images_palette_fragment);
    }

    /**
     * Initialize the variables that will support the render of the fragment
     *
     * @param savedInstanceState bundle with last tags and paths which allow us restore the UI
     */
    private void loadData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.paths = new String[IMAGES_SIZE];
            this.tags = new String[IMAGES_SIZE];
        } else {
            /*Load info from bundle*/
            this.paths = savedInstanceState.getStringArray(PATHS_KEY);
            this.tags = (Object[]) savedInstanceState.getSerializable(TAGS_KEY);
            int[] imagesVisibility = savedInstanceState.getIntArray(VISIBILITY_FLAGS_KEY);
            /*Update UI with information read*/
            updateUIDrawables();
            updateUITags();
            updateUIVisibility(imagesVisibility);
        }
    }

    /**
     * Set the the on listeners of the views that compose the fragment view
     * <p/>
     * Previous to honeycomb api
     */
    private void setOldEvents() {
        ClickableListener clickableListener = new ClickableListener();
        clickableListener.setTarget(target);

        for (int i = 0; i < this.selectableImageList.size(); i++) {
            this.selectableImageList.get(i).setOnClickListener(clickableListener);
        }
    }


    /**
     * Set the the on listeners of the views that compose the fragment view
     * <p/>
     * After honeycomb API
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setHoneycombEvents() {
        DraggableListener draggableListener = new DraggableListener();

        for (int i = 0; i < selectableImageList.size(); i++) {
            this.selectableImageList.get(i).setOnTouchListener(draggableListener);
        }

        //The the palette itself as droppable so in this case makes nothing
        DroppableListener droppableListener = new DroppableListener();
        droppableListener.setDropAction(DropAction.NOTHING);

        this.imagesPalette.setOnDragListener(droppableListener);
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.bindViews();
        this.loadData(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            this.setOldEvents();
        } else {
            this.setHoneycombEvents();
        }
    }

    /**
     * Save the state of the instance
     *
     * @param stateOut Bundle that is going to keep the state of the palette of images
     */
    public void onSaveInstanceState(Bundle stateOut) {
        super.onSaveInstanceState(stateOut);

        stateOut.putStringArray(PATHS_KEY, this.paths);
        stateOut.putSerializable(TAGS_KEY, this.tags);
        stateOut.putIntArray(VISIBILITY_FLAGS_KEY, getImagesVisibility());
    }

    /**
     * Load the images in the paths provide
     * And set the respective view in the UI
     */
    private void updateUIDrawables() {
        if ((selectableImageList != null) && (paths != null) && (paths.length >= 0)) {
            Drawable[] drawables = ImageHelper.loadImages(getContext(), paths);
            for (int i = 0; i < this.selectableImageList.size(); i++) {
                selectableImageList.get(i).setImageDrawable(drawables[i]);
            }
        }
    }

    /**
     * Set the images as visible
     */
    private void showImages() {
        if ((this.selectableImageList != null) && (this.selectableImageList.size() > 0)) {
            for (int i = 0; i < this.selectableImageList.size(); i++) {
                this.selectableImageList.get(i).setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Set the drawables to set in the palette
     *
     * @param paths of the drawables to show in the palette
     */
    @Override
    public void setDrawablesPaths(String[] paths) {
        this.paths = paths;
        this.updateUIDrawables();
        this.showImages();
    }

    /**
     * Uses the tags that has
     */
    private void updateUITags() {
        if ((this.selectableImageList != null) && (tags != null) && (tags.length >= 0)) {
            for (int i = 0; i < selectableImageList.size(); i++) {
                this.selectableImageList.get(i).setTag(tags[i]);
            }
        }
    }

    /**
     * @return An array of the current visibilities of the images in the palette of images
     */
    private int[] getImagesVisibility() {
        if ((this.selectableImageList == null) || (this.selectableImageList.isEmpty())) {
            return null;
        } else {
            int[] visibilities = new int[selectableImageList.size()];
            for (int i = 0; i < selectableImageList.size(); i++) {
                visibilities[i] = this.selectableImageList.get(i).getVisibility();
            }
            return visibilities;
        }
    }

    /**
     * Uses the flags of visibility to update the state of the UI
     *
     * @param imagesVisibility Visibility of the images in the palette
     */
    private void updateUIVisibility(int[] imagesVisibility) {
        for (int i = 0; i < imagesVisibility.length; i++) {
            this.selectableImageList.get(i).setVisibility(imagesVisibility[i]);
        }
    }

    /**
     * Set the tags of the image views
     *
     * @param tags Tags of the images
     */
    @Override
    public void setTags(Object[] tags) {
        this.tags = tags;
        updateUITags();
    }

    /**
     * Set the view that is going to be set when the user click in one image to choose in
     * the palette
     *
     * @param target View that is going to get the selected option
     */
    @Override
    public void setTarget(ISelectedChoice target) {
        this.target = target;
    }
}
