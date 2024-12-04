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
package com.example.weatherupdates.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherupdates.data.remote.dtos.current.Current
import com.example.weatherupdates.data.remote.dtos.current.Location
import com.example.weatherupdates.data.remote.dtos.weatherdetails.Hour
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(private val gson : Gson)  {

    @TypeConverter
    fun fromCurrent(current: Current): String {
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(currentString: String): Current {
        val type = object : TypeToken<Current>() {}.type
        return gson.fromJson(currentString, type)
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(locationString: String): Location {
        val type = object : TypeToken<Location>() {}.type
        return gson.fromJson(locationString, type)
    }

    @TypeConverter
    fun fromHourList(hourList: List<Hour>): String {
        return gson.toJson(hourList)
    }

    @TypeConverter
    fun toHourList(hourListString: String): List<Hour> {
        val type = object : TypeToken<List<Hour>>() {}.type
        return gson.fromJson(hourListString, type)
    }
}
