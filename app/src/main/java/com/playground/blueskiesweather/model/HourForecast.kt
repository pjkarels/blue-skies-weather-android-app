package com.playground.blueskiesweather.model

data class HourForecast(val temp: Double,
                        val conditions: String,
                        val time: String,
                        val icon: String,
                        val wind: String,
                        val precipitationChance: Int,
                        val forecast: String
) {
}