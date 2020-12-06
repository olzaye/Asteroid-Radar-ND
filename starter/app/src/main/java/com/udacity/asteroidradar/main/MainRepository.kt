package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.room.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class MainRepository(private val database: AsteroidDatabase) {

    suspend fun saveAsteroidData(queryMap: Map<String, String>) {
        withContext(Dispatchers.IO) {
            try {
                val response = AsteroidApi.retrofitService.getAsteroidInformation(queryMap)
                response?.let {
                    val list = parseAsteroidsJsonResult(JSONObject(it))
                    database.asteroidDatabaseDao.insertAllAsteroid(*list.toTypedArray())
                }
            } catch (e: Exception) {
                Log.e("MainRepository", "error: ${e.message}")
            }
        }
    }

    fun getAsteroidFromDb(startDate: String): LiveData<List<Asteroid>> {
        return database.asteroidDatabaseDao.getAsteroids(startDate)
    }

    suspend fun delete(startDate: String) {
        withContext(Dispatchers.IO) {
            database.asteroidDatabaseDao.deleteOldAsteroids(startDate)
        }
    }
}