package com.udacity.asteroidradar.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDatabaseDao {

    @Query("SELECT * FROM ${Constants.TABLE_NAME} WHERE  close_approach_date >= :startDate ORDER BY close_approach_date ASC")
    fun getAsteroids(startDate: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(vararg asteroid: Asteroid)

    @Query("DELETE FROM ${Constants.TABLE_NAME} WHERE  close_approach_date < :startDate")
    fun deleteOldAsteroids(startDate: String)
}