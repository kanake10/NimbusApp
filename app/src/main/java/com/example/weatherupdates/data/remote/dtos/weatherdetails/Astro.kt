package com.example.weatherupdates.data.remote.dtos.weatherdetails

data class Astro(
    val moon_illumination: Int,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)