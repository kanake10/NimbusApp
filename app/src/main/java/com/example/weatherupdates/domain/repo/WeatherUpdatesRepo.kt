package com.example.weatherupdates.domain.repo

import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.domain.models.CurrentWeatherDomain
import com.example.weatherupdates.domain.models.WeatherDetailsDomain
import kotlinx.coroutines.flow.Flow

interface WeatherUpdatesRepo {
    fun fetchCurrentWeatherUpdates(): Flow<Resource<CurrentWeatherDomain>>
    fun fetchWeatherForDate(date:String): Flow<Resource<WeatherDetailsDomain>>
}