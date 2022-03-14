package com.playground.blueskiesweather.model

data class HourForecast(val temp: Int,
                        val conditions: String,
                        val time: String,
                        val icon: String,
                        val wind: String,
                        val precipitationChance: Int,
                        val forecast: String
) {
}