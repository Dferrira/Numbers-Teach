package com.dferreira.numbers_teach.generic_exercise

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.drag_and_drop.draggable_listener.DraggableListener
import com.dferreira.numbers_teach.drag_and_drop.clickable_listener.ClickableListenerFactory
import com.dferreira.numbers_teach.drag_and_drop.drag_and_drop_listener.NeutralDragAndDropListenerImpl
import com.dferreira.numbers_teach.helpers.ImageHelper

/**
 * Support a bunch of images that can be changed dynamically
 */
class ImagesPaletteFragment : Fragment(), IImagesPalette {
    private lateinit var imagesPalette: ViewGroup
    private lateinit var selectableImageList: List<ImageView>

    /*Listener that is going to get the click of the user*/
    private lateinit var target: ISelectedChoice

    /*List of paths of the images in the palette*/
    private lateinit var paths: Array<String>

    /*List of tags that are in the palette of images*/
    private lateinit var tags: Array<Any>

    /**
     * Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater           Responsible for inflating the view
     * @param container          container where the fragment is going to be included
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     * @return the view with images for the user to pick or click
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.images_palette_fragment, container, false)
    }

    /**
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        loadData(savedInstanceState)
        setEvents()
    }

    private fun setEvents() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            setOldEvents()
        } else {
            setHoneycombEvents()
        }
    }

    /**
     * Set the the on listeners of the views that compose the fragment view
     *
     *
     * Previous to honeycomb api
     */
    private fun setOldEvents() {
        val clickableListenerFactory = ClickableListenerFactory()
        val clickableListener = clickableListenerFactory.createClickableListener(target)
        selectableImageList.forEach { selectableImage ->
            selectableImage.setOnClickListener(clickableListener)
        }
    }

    /**
     * Set the the on listeners of the views that compose the fragment view
     *
     *
     * After honeycomb API
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun setHoneycombEvents() {
        val draggableListener = DraggableListener()
        selectableImageList.forEach { selectableImage ->
            selectableImage.setOnTouchListener(draggableListener)
        }

        //The the palette itself as droppable so in this case makes nothing
        val neutralDragAndDropListener = NeutralDragAndDropListenerImpl()
        imagesPalette.setOnDragListener(neutralDragAndDropListener)
    }


    /**
     * Bind the the views to the respective variables
     */
    private fun bindViews(view: View) {
        /* Get the images reference */
        val imageIdList = listOf(
            R.id.opt_img1,
            R.id.opt_img2,
            R.id.opt_img3,
            R.id.opt_img4
        )

        selectableImageList = imageIdList
            .map { imageId -> view.findViewById(imageId) }

        /*Get the layout reference */
        imagesPalette = view as ViewGroup
    }

    /**
     * Initialize the variables that will support the render of the fragment
     *
     * @param savedInstanceState bundle with last tags and paths which allow us restore the UI
     */
    private fun loadData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            paths = emptyArray()
            tags = emptyArray()
        } else {
            /*Load info from bundle*/
            paths = savedInstanceState.getStringArray(PATHS_KEY) as Array<String>
            tags = savedInstanceState.getSerializable(TAGS_KEY) as Array<Any>
            val imagesVisibility = savedInstanceState.getIntArray(VISIBILITY_FLAGS_KEY)
            /*Update UI with information read*/updateUIDrawables()
            updateUITags()
            updateUIVisibility(imagesVisibility)
        }
    }


    /**
     * Save the state of the instance
     *
     * @param stateOut Bundle that is going to keep the state of the palette of images
     */
    override fun onSaveInstanceState(stateOut: Bundle) {
        super.onSaveInstanceState(stateOut)
        stateOut.putStringArray(
            PATHS_KEY,
            paths
        )
        stateOut.putSerializable(
            TAGS_KEY,
            tags
        )
        var imageVisibilityList = createImageVisibilityList()
            .toIntArray()
        stateOut.putIntArray(
            VISIBILITY_FLAGS_KEY,
            imageVisibilityList
        )
    }

    /**
     * Load the images in the paths provide
     * And set the respective view in the UI
     */
    private fun updateUIDrawables() {
        if (paths.isEmpty()) {
            return
        }
        val drawables = ImageHelper.loadImages(
            context, paths
        )
        selectableImageList.zip(drawables) { selectableImage, drawable ->
            selectableImage.setImageDrawable(drawable)
        }
    }

    /**
     * Set the images as visible
     */
    private fun showImages() {
        selectableImageList.forEach {
            it.visibility = View.VISIBLE
        }
    }

    /**
     * Set the drawables to set in the palette
     *
     * @param paths of the drawables to show in the palette
     */
    override fun setDrawablesPaths(paths: Array<String>) {
        this.paths = paths
        updateUIDrawables()
        showImages()
    }

    /**
     * Uses the tags that has
     */
    private fun updateUITags() {
        selectableImageList.zip(tags) {
            selectableImage, tag -> selectableImage.tag = tag
        }
    }

    /**
     * @return An array of the current visibilities of the images in the palette of images
     */
    private fun createImageVisibilityList(): List<Int> {
        return if (selectableImageList.isEmpty()) {
            emptyList()
        } else {
            selectableImageList
                .map { selectableImage -> selectableImage.visibility }
        }
    }


    /**
     * Uses the flags of visibility to update the state of the UI
     *
     * @param imagesVisibility Visibility of the images in the palette
     */
    private fun updateUIVisibility(imagesVisibility: IntArray?) {
        for (i in imagesVisibility!!.indices) {
            selectableImageList!![i].visibility = imagesVisibility[i]
        }
    }

    /**
     * Set the tags of the image views
     *
     * @param tags Tags of the images
     */
    override fun setTags(tags: Array<Any>) {
        this.tags = tags
        updateUITags()
    }

    /**
     * Set the view that is going to be set when the user click in one image to choose in
     * the palette
     *
     * @param target View that is going to get the selected option
     */
    override fun setTarget(target: ISelectedChoice) {
        this.target = target
    }

    companion object {
        private const val IMAGES_SIZE = 4
        private const val PATHS_KEY = "PATHS"
        private const val VISIBILITY_FLAGS_KEY = "VISIBILITY_FLAGS"
        private const val TAGS_KEY = "TAGS"
    }
}