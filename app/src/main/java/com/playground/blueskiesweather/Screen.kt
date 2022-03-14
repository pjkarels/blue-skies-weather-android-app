package com.playground.blueskiesweather

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object DailyScreen: Screen("day", R.string.bottom_bar_item_daily)
    object HourlyScreen: Screen("hour", R.string.bottom_bar_item_hourly)
    object MapsScreen: Screen("maps", R.string.bottom_bar_item_maps)
}
