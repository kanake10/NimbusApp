package com.example.weatherupdates.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherupdates.data.remote.dtos.weatherdetails.Hour

@Entity(tableName = "weather_details")
data class WeatherDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val date: String,
    val hour: List<Hour>
)
