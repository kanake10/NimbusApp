package com.example.weatherupdates.domain.models


import com.example.weatherupdates.data.remote.dtos.weatherdetails.Hour

data class WeatherDetailsDomain (
    val date: String,
    val hour: List<Hour>
)
