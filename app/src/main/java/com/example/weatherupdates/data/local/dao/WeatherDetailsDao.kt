package com.example.weatherupdates.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherupdates.data.local.entity.WeatherDetailsEntity


@Dao
interface WeatherDetailsDao {
    @Query("SELECT * FROM weather_details WHERE date = :date")
    suspend fun getWeatherByDate(date: String): WeatherDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherData(weatherEntity: WeatherDetailsEntity)

    @Query("DELETE FROM weather_details")
    suspend fun deleteWeatherDetails()
}