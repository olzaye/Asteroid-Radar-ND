package com.udacity.asteroidradar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.Asteroid


@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDatabaseDao: AsteroidDatabaseDao

    companion object {

        private lateinit var INSTANCE: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        Constants.TABLE_NAME
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}