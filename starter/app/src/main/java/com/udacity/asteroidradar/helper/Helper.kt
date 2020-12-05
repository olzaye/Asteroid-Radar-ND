package com.udacity.asteroidradar.helper

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*


fun getQueryParamMap(): Map<String, String> {
    return mapOf(
        Constants.START_DATE_QUERY_KEY to getFormattedCurrentTime()
    )
}

fun getDateFormat(): SimpleDateFormat =
    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

fun getFormattedCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val startTime = calendar.time
    return getDateFormat().format(startTime)
}