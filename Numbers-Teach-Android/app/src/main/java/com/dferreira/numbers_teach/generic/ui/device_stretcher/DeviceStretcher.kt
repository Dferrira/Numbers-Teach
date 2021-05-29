package com.dferreira.numbers_teach.generic.ui.device_stretcher

import android.graphics.drawable.Drawable
import java.io.InputStream

interface DeviceStretcher {
    fun zoomDrawable(imgStream: InputStream): Drawable
}