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
package com.example.weatherupdates.di

import android.content.Context
import androidx.room.Room
import com.example.weatherupdates.core.Constants.BASE_URL
import com.example.weatherupdates.core.Constants.DB_NAME
import com.example.weatherupdates.data.local.converters.Converters
import com.example.weatherupdates.data.local.db.WeatherUpdateDatabase
import com.example.weatherupdates.data.remote.api.WeatherUpdatesApi
import com.example.weatherupdates.data.remote.utils.Interceptor
import com.example.weatherupdates.data.repo.WeatherUpdatesRepoImpl
import com.example.weatherupdates.domain.repo.WeatherUpdatesRepo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideConverters(gson: Gson) = Converters(gson)

    @Provides
    @Singleton
    fun provideWeatherUpdatesDatabase(
        @ApplicationContext context: Context,
        converters: Converters
    ): WeatherUpdateDatabase {
        return Room.databaseBuilder(
            context,
            WeatherUpdateDatabase::class.java,
            DB_NAME
        )
            .addTypeConverter(converters)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherUpdatesRepository(
        weatherUpdatesApi: WeatherUpdatesApi,
        weatherUpdateDatabase: WeatherUpdateDatabase
    ): WeatherUpdatesRepo {
        return WeatherUpdatesRepoImpl(
            weatherUpdatesApi = weatherUpdatesApi,
            currentWeatherDao =weatherUpdateDatabase.currentWeatherDao(),
            weatherDetailsDao = weatherUpdateDatabase.weatherDetailsDao()
        )
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }



    @Provides
    @Singleton
    fun provideWeatherUpdatesApi(okHttpClient: OkHttpClient): WeatherUpdatesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(WeatherUpdatesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            //.addInterceptor(Interceptor())
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
        return okHttpClient.build()
    }
}