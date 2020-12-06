package com.udacity.asteroidradar.helper

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*


fun getQueryParamMap(): Map<String, String> {
    return mapOf(
        Constants.START_DATE_QUERY_KEY to getFormattedCurrentTime(),
        Constants.END_DATE_QUERY_KEY to getSeventhDateFromNow()
    )
}

fun getDateFormat(): SimpleDateFormat =
    SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

fun getFormattedCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val startTime = calendar.time
    return getDateFormat().format(startTime)
}

fun getSeventhDateFromNow(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val seventhDateFromNow = calendar.time

    return getDateFormat().format(seventhDateFromNow)
}