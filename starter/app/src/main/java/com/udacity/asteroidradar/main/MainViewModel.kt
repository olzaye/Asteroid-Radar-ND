package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidApi.retrofitWithMoshiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val _asteroidList = MutableLiveData<ArrayList<Asteroid>>()
    val asteroidList: LiveData<ArrayList<Asteroid>>
        get() = _asteroidList

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        getAsteroids()
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch {
            val response = retrofitWithMoshiService.getNasaImage()
            response?.let {
                _pictureOfDay.value = it
            } ?: run {
                Log.e("MainViewModel", "picture of day response is null")
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            val response = AsteroidApi.retrofitService.getAsteroidInformation(getQueryParamMap())
            response?.let {
                val list = parseAsteroidsJsonResult(JSONObject(it))
                _asteroidList.value = list
            } ?: run {
                Log.e("MainViewModel", "response is null")
            }
        }
    }

    private fun getQueryParamMap(): Map<String, String> {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        val startTime = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val endTime = calendar.time

        return mapOf(
            Constants.START_DATE_QUERY_KEY to dateFormat.format(startTime),
            Constants.END_DATE_QUERY_KEY to dateFormat.format(endTime)
        )
    }
}