package com.playground.blueskiesweather.api

import javax.inject.Inject

class WeatherApiClient @Inject constructor(val api: WeatherApi) {

    suspend fun getPoints(lat: Double, long: Double): Result<Any> {
        return api.getPoints(lat, long)
    }

}