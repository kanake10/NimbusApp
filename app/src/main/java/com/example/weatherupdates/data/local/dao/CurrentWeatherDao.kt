package com.example.weatherupdates.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherupdates.data.local.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {
    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeatherDetails(): CurrentWeatherEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherDetails(currentWeatherEntity: CurrentWeatherEntity)

    @Query("DELETE FROM current_weather")
    suspend fun deleteCurrentWeatherDetails()
}