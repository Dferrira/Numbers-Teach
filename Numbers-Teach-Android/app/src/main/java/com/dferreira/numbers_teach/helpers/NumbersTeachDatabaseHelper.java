package com.dferreira.numbers_teach.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dferreira.numbers_teach.models.ExerciseResult;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Handles the opening of the lite database
 */
public class NumbersTeachDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "numbers_teach_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    private static NumbersTeachDatabaseHelper instance;

    /**
     * The data access object used to interact with the Sqlite database.
     */
    private Dao<ExerciseResult, Long> exerciseResultDao;

    /**
     * @param context Context where this helper will be used
     */
    private NumbersTeachDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Get an instance of the database helper
     *
     * @param context Context where this method will be used
     * @return an instance to the database helper
     */
    public synchronized static NumbersTeachDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NumbersTeachDatabaseHelper(context);
        }
        return instance;
    }

    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource To use get connections to the database to be created.
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ExerciseResult.class);

        } catch (SQLException e) {
            Log.d(TAG, "Impossible create db", e);
        }
    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     * <p/>
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion       The version that we are upgrading the database to.
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            /**
             * Recreates the database when onUpgrade is called by the framework
             */
            TableUtils.dropTable(connectionSource, ExerciseResult.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return An instance of the data access object of the  exerciseResult entity
     * @throws SQLException
     */
    public Dao<ExerciseResult, Long> getExerciseResultDao() throws SQLException {
        if (exerciseResultDao == null) {
            exerciseResultDao = getDao(ExerciseResult.class);
        }
        return exerciseResultDao;
    }
}