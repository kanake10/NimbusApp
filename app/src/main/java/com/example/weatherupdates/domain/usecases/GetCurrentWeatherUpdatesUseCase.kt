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