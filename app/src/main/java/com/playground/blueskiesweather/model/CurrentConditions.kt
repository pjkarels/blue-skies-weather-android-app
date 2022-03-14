package com.playground.blueskiesweather.model

class CurrentConditions(val temp: Int,
                        val wind: String,
                        val icon: String,
                        val feelsLike: Int,
                        val dewPoint: Int,
                        val humidity: Int,
                        val forecast: String
) {
}