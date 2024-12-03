package com.example.weatherupdates.data.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherupdates.data.local.entity.CurrentWeatherEntity
import com.example.weatherupdates.data.local.converters.Converters
import com.example.weatherupdates.data.local.dao.CurrentWeatherDao
import com.example.weatherupdates.data.local.dao.WeatherDetailsDao
import com.example.weatherupdates.data.local.entity.WeatherDetailsEntity

@TypeConverters(Converters::class)
@Database(
    entities = [CurrentWeatherEntity::class,WeatherDetailsEntity::class],
    version = 2,
    exportSchema = false
)

abstract class WeatherUpdateDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherDetailsDao() : WeatherDetailsDao
}