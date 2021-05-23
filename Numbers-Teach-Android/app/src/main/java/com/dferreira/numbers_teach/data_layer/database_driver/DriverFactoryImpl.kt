package com.dferreira.numbers_teach.data_layer.database_driver

import android.content.Context
import com.dferreira.numbers_teach.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DriverFactoryImpl(private val context: Context): DriverFactory {
    override fun createDriver(): SqlDriver {
        val applicationContext = context.applicationContext
        return AndroidSqliteDriver(Database.Schema, applicationContext, DatabaseFileName)
    }

    companion object {
        const val DatabaseFileName = "numbers_teach.db"
    }
}