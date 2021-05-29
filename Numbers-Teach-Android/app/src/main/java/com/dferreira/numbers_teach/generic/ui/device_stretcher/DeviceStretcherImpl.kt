package com.dferreira.numbers_teach.generic.ui.device_stretcher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.WindowManager
import java.io.InputStream

/**
 * Stretch the number image to show correctly in any device
 */
class DeviceStretcherImpl(val context: Context) : DeviceStretcher {

    private var matrix: Matrix = Matrix()

    /**
     * Initializes the matrix that will stretch the images
     *
     * @param heightDevice Height from the current device
     */
    private fun initializeMatrixTransformation(heightDevice: Int) {
        val zoom = (1.0 * heightDevice / HEIGHT_DISPLAY_BASE).toFloat()
        matrix.reset()
        matrix.postScale(zoom, zoom)
    }

    /**
     * @return the width from the current device
     */
    private fun getDisplayHeight(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        return display.height
    }

    /**
     * @param imgStream Stream of the file that points to the original image original draw image
     * @return image stretched
     */
    override fun zoomDrawable(imgStream: InputStream): Drawable {
        var originalBitmap: Bitmap? = null
        return try {
            originalBitmap = BitmapFactory.decodeStream(imgStream)
            val scaledBitmap = Bitmap.createBitmap(
                originalBitmap,
                0,
                0,
                originalBitmap.width,
                originalBitmap.height,
                matrix,
                true
            )
            BitmapDrawable(context.resources, scaledBitmap)
        } finally {
            originalBitmap?.recycle()
        }
    }

    companion object {
        private const val HEIGHT_DISPLAY_BASE = 850
    }

    init {
        val heightDevice = getDisplayHeight()
        initializeMatrixTransformation(heightDevice)
    }
}