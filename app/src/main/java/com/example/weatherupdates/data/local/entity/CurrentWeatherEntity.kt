package com.example.weatherupdates.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherupdates.data.remote.dtos.current.Current
import com.example.weatherupdates.data.remote.dtos.current.Location

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val current: Current,
    val location: Location
)
