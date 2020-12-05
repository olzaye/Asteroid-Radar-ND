package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.helper.getQueryParamMap
import com.udacity.asteroidradar.main.MainRepository
import com.udacity.asteroidradar.room.AsteroidDatabase
import java.lang.Exception

class NewestAsteroidWorker(private val appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "NewestAsteroidWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            val repository = MainRepository(AsteroidDatabase.getInstance(appContext))
            repository.saveAsteroidData(getQueryParamMap())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}