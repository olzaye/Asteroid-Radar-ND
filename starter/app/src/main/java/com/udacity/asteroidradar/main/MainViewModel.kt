package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.AsteroidApi.retrofitWithMoshiService
import com.udacity.asteroidradar.helper.getFormattedCurrentTime
import com.udacity.asteroidradar.helper.getQueryParamMap
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.room.AsteroidDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = MainRepository(database)

    val asteroidList: LiveData<List<Asteroid>>
        get() = getAsteroids()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        saveAsteroids()
        getImages()
        getAsteroids()

        viewModelScope.launch {
            repository.delete(getFormattedCurrentTime())
        }
    }

    private fun getAsteroids(): LiveData<List<Asteroid>> {
        return repository.getAsteroidFromDb(getFormattedCurrentTime())
    }

    private fun getImages() {
        viewModelScope.launch {
            try {
                val response = retrofitWithMoshiService.getNasaImage()
                response?.let {
                    _pictureOfDay.value = it
                } ?: run {
                    Log.e("MainViewModel", "picture of day response is null")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "picture of day error ${e.message}")
            }
        }
    }

    private fun saveAsteroids() {
        viewModelScope.launch {
            try {
                repository.saveAsteroidData(getQueryParamMap())
            } catch (e: Exception) {
                Log.e("MainViewModel", "error by saveAsteroids ${e.message}")
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}