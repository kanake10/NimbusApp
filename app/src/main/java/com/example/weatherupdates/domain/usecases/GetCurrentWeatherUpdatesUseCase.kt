package com.example.weatherupdates.domain.usecases

import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.domain.models.CurrentWeatherDomain
import com.example.weatherupdates.domain.repo.WeatherUpdatesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUpdatesUseCase @Inject constructor(private val weatherUpdatesRepo: WeatherUpdatesRepo) {
    operator fun invoke(): Flow<Resource<CurrentWeatherDomain>> {
        return weatherUpdatesRepo.fetchCurrentWeatherUpdates()
    }
}