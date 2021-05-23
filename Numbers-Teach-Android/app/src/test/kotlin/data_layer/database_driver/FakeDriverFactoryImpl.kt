package data_layer.database_driver

import com.dferreira.numbers_teach.Database
import com.dferreira.numbers_teach.data_layer.database_driver.DriverFactory
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

class FakeDriverFactoryImpl: DriverFactory {
    override fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        return driver
    }
}