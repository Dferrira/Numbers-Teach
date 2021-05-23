package com.dferreira.numbers_teach.data_layer.database_driver

import com.squareup.sqldelight.db.SqlDriver

interface DriverFactory {
    fun createDriver(): SqlDriver
}