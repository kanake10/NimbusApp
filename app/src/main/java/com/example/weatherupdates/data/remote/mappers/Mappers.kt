/*
 * Copyright 2024 Ezra Kanake.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    val forecastDay = forecast.forecastday.firstOrNull()
    val firstHour = forecastDay?.hour ?: emptyList()


    return WeatherDetailsEntity(
        id = 0,
        date = forecastDay?.date ?: "",
        hour = firstHour
    )
}


internal fun WeatherDetailsEntity.toDomain() : WeatherDetailsDomain {
    return WeatherDetailsDomain(
        date, hour
    )
}