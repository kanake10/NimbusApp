package com.example.weatherupdates.data.remote.mappers

import com.example.weatherupdates.data.local.entity.CurrentWeatherEntity
import com.example.weatherupdates.data.local.entity.WeatherDetailsEntity
import com.example.weatherupdates.data.remote.dtos.current.CurrentWeatherDto
import com.example.weatherupdates.data.remote.dtos.weatherdetails.WeatherDetailsDto
import com.example.weatherupdates.domain.models.CurrentWeatherDomain
import com.example.weatherupdates.domain.models.WeatherDetailsDomain

internal fun CurrentWeatherDto.toEntity(): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        id = 0,
        current, location
    )
}


internal fun CurrentWeatherEntity.toDomain(): CurrentWeatherDomain {
    return CurrentWeatherDomain(
        current, location
    )
}


internal fun WeatherDetailsDto.toEntity(): WeatherDetailsEntity {
    // Assuming we want the first forecast day
    val forecastDay = forecast.forecastday.firstOrNull()  // Get the first forecast day
    val firstHour = forecastDay?.hour ?: emptyList()  // Get the list of hours, or an empty list


    return WeatherDetailsEntity(
        id = 0,  // Generate or extract the ID
        date = forecastDay?.date ?: "",  // Extract the date from the first forecast day
        hour = firstHour  // Get the hour list from the first forecast day
    )
}


internal fun WeatherDetailsEntity.toDomain() : WeatherDetailsDomain {
    return WeatherDetailsDomain(
        date, hour
    )
}