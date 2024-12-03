package com.example.weatherupdates.domain.usecases

import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.domain.models.WeatherDetailsDomain
import com.example.weatherupdates.domain.repo.WeatherUpdatesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDetailsUseCase @Inject constructor(private val weatherUpdatesRepo: WeatherUpdatesRepo) {
    operator fun invoke (date : String): Flow<Resource<WeatherDetailsDomain>> {
        return weatherUpdatesRepo.fetchWeatherForDate(date)
    }
}