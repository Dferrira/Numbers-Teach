package data_layer.database_driver

import com.dferreira.numbers_teach.Database
import com.squareup.sqldelight.db.SqlDriver

class FakeDataBaseFactory {


    fun createDatabase(): Database {
        val driver = createDriver()
        return Database(driver)
    }


    private fun createDriver(): SqlDriver {
        val driverFactory = FakeDriverFactoryImpl()
        return driverFactory.createDriver()
    }

}