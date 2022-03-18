package com.playground.blueskiesweather.api

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("points/{lat},{long}")
    suspend fun getPoints(@Path("lat") lat: Double, @Path("long") long: Double): Result<Any>
}