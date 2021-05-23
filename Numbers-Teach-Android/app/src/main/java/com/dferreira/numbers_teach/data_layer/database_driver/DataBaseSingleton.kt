package com.dferreira.numbers_teach.data_layer.database_driver

import android.content.Context
import com.dferreira.numbers_teach.Database
import com.squareup.sqldelight.db.SqlDriver

object DataBaseSingleton {
    private var driver: SqlDriver? = null
    private var database: Database? = null


    fun getDatabase(context: Context): Database {
        return database ?: createDatabase(context)
    }

    private fun createDatabase(context: Context): Database {
        val driver = getDriver(context)
        val newDatabase = Database(driver)
        database = newDatabase
        return newDatabase
    }

    private fun getDriver(context: Context): SqlDriver {
        return driver ?: createDriver(context)
    }

    private fun createDriver(context: Context): SqlDriver {
        val driverFactory = DriverFactoryImpl(context)
        val newDriver = driverFactory.createDriver()
        driver = newDriver
        return newDriver
    }

}