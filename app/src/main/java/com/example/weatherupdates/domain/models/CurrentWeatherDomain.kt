package com.example.weatherupdates.domain.models

import com.example.weatherupdates.data.remote.dtos.current.Current
import com.example.weatherupdates.data.remote.dtos.current.Location

data class CurrentWeatherDomain(
    val current: Current,
    val location: Location
)
