package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.work.NewestAsteroidWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApp : Application() {

    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        appScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true).build()

            val workRequest = PeriodicWorkRequestBuilder<NewestAsteroidWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                NewestAsteroidWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}