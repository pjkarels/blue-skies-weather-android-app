package com.playground.blueskiesweather.model

data class DayForecast(val highTemp: Int,
                       val lowTemp: Int,
                       val date: String,
                       val conditions: String,
                       val dayIcon: String,
                       val nightIcon: String,
                       val precipitationChance: Int,
                       val forecast: String
) {
}