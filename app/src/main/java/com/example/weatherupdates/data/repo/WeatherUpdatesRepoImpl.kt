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
package com.example.weatherupdates.data.repo

import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.data.local.dao.CurrentWeatherDao
import com.example.weatherupdates.data.remote.api.WeatherUpdatesApi
import com.example.weatherupdates.data.remote.mappers.toDomain
import com.example.weatherupdates.data.remote.mappers.toEntity
import com.example.weatherupdates.domain.models.CurrentWeatherDomain
import com.example.weatherupdates.domain.repo.WeatherUpdatesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import android.util.Log
import com.example.weatherupdates.data.local.dao.WeatherDetailsDao
import com.example.weatherupdates.domain.models.WeatherDetailsDomain

class WeatherUpdatesRepoImpl(
    private val weatherUpdatesApi: WeatherUpdatesApi,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherDetailsDao: WeatherDetailsDao
) : WeatherUpdatesRepo {

    override fun fetchCurrentWeatherUpdates(): Flow<Resource<CurrentWeatherDomain>> = flow {
        Log.d("WeatherUpdatesRepo", "Fetching current weather updates...")
        emit(Resource.Loading())

        val localCurrentWeatherData = currentWeatherDao.getCurrentWeatherDetails()?.toDomain()
        Log.d("WeatherUpdatesRepo", "Local data fetched: $localCurrentWeatherData")

        localCurrentWeatherData?.let {
            Log.d("WeatherUpdatesRepo", "Emitting local data as success")
            emit(Resource.Success(it))
        }

        try {
            Log.d("WeatherUpdatesRepo", "Calling weather updates API...")
            val currentWeatherApiResponse = weatherUpdatesApi.getCurrentWeather()
            Log.d("WeatherUpdatesRepo", "API response: $currentWeatherApiResponse")

            Log.d("WeatherUpdatesRepo", "Inserting API data into local database")
            val entity = currentWeatherApiResponse.toEntity()
            currentWeatherDao.insertCurrentWeatherDetails(entity)

            val updatedData = currentWeatherDao.getCurrentWeatherDetails()?.toDomain()
            updatedData?.let {
                Log.d("WeatherUpdatesRepo", "Emitting updated data as success")
                emit(Resource.Success(it))
            }
        } catch (exception: IOException) {
            Log.e("WeatherUpdatesRepo", "IOException: ${exception.message}")
            emit(Resource.Error("Connection Lost", data = localCurrentWeatherData))
        } catch (exception: HttpException) {
            Log.e("WeatherUpdatesRepo", "HttpException: ${exception.message()}")
            emit(Resource.Error(exception.message() ?: "An unexpected error occurred", data = localCurrentWeatherData))
        } catch (exception: Exception) {
            Log.e("WeatherUpdatesRepo", "Exception: ${exception.message}")
            emit(Resource.Error("An unexpected error occurred", data = localCurrentWeatherData))
        }
    }

    override fun fetchWeatherForDate(date: String): Flow<Resource<WeatherDetailsDomain>> = flow {
        Log.d("WeatherUpdatesRepo", "Fetching weather for date: $date...")
        emit(Resource.Loading())

        val localWeatherData = weatherDetailsDao.getWeatherByDate(date)?.toDomain()
        Log.d("WeatherUpdatesRepo", "Local data for date $date fetched: $localWeatherData")

        localWeatherData?.let {
            Log.d("WeatherUpdatesRepo", "Emitting local data for date $date as success")
            emit(Resource.Success(it))
        }

        try {
            Log.d("WeatherUpdatesRepo", "Calling weather updates API for date: $date...")
            val weatherApiResponse = weatherUpdatesApi.getWeatherDetails(date)
            Log.d("WeatherUpdatesRepo", "API response for date $date: $weatherApiResponse")

            Log.d("WeatherUpdatesRepo", "Inserting API data into local database for date $date")
            val entity = weatherApiResponse.toEntity()
            weatherDetailsDao.saveWeatherData(entity)

            val updatedData = weatherDetailsDao.getWeatherByDate(date)?.toDomain()
            updatedData?.let {
                Log.d("WeatherUpdatesRepo", "Emitting updated data for date $date as success")
                emit(Resource.Success(it))
            }
        } catch (exception: IOException) {
            Log.e("WeatherUpdatesRepo", "IOException: ${exception.message}")
            emit(Resource.Error("Connection Lost", data = localWeatherData))
        } catch (exception: HttpException) {
            Log.e("WeatherUpdatesRepo", "HttpException: ${exception.message()}")
            emit(Resource.Error(exception.message() ?: "An unexpected error occurred", data = localWeatherData))
        } catch (exception: Exception) {
            Log.e("WeatherUpdatesRepo", "Exception: ${exception.message}")
            emit(Resource.Error("An unexpected error occurred", data = localWeatherData))
        }
    }
}

