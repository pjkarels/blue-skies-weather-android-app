package com.playground.blueskiesweather.repository

import com.playground.blueskiesweather.api.WeatherApiClient
import javax.inject.Inject

class ForecastRepository @Inject constructor(val apiClient: WeatherApiClient) {

    suspend fun getPoints(lat: Double, long: Double) {
        val result = apiClient.getPoints(lat, long)
        val resultResult = result.getOrNull()
    }
}