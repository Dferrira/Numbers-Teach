package com.dferreira.numbers_teach.generic.ui.device_stretcher

import android.content.Context

class DeviceStretcherFactory {
    fun createDeviceStretcher(context: Context): DeviceStretcher
    {
        val applicationContext = context.applicationContext
        return DeviceStretcherImpl(applicationContext)
    }
}