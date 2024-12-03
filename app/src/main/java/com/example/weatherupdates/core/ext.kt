package com.example.weatherupdates.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatHourAndMinute(dateTimeString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}