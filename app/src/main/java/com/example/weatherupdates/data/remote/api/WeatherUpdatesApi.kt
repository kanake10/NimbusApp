package com.example.weatherupdates.data.remote.api

import com.example.weatherupdates.data.remote.dtos.current.CurrentWeatherDto
import com.example.weatherupdates.data.remote.dtos.weatherdetails.WeatherDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherUpdatesApi {

    @GET("history.json")
    suspend fun getWeatherDetails(@Query("dt") date: String,
                                  @Query("key") key: String = "a236ebb90d17481da56144752240212",
                                  @Query("q") q:String = "Nairobi"
                                  ): WeatherDetailsDto

    @GET("current.json")
        suspend fun getCurrentWeather(@Query("key") key: String = "a236ebb90d17481da56144752240212",
                                  @Query("q") q: String = "Nairobi"): CurrentWeatherDto
}