package com.playground.blueskiesweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.playground.blueskiesweather.model.DayForecast
import com.playground.blueskiesweather.model.HourForecast
import com.playground.blueskiesweather.ui.theme.BlueSkiesWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueSkiesWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomBar(navController, listOf()) }
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            Screen.DailyScreen.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.DailyScreen.route) { DailyScreenUI(dailies = listOf(
                                DayForecast(
                                70.0, 30.0, "Mar 14", "Clear", "", "", 10,
                                "Cloudy with a chance of meatballs."
                            )
                            )) }
                            composable(Screen.HourlyScreen.route) { HourlyScreen(hourlies = listOf(
                                HourForecast(
                                    40.0, "Cloudy", "1PM", "", "NE 10mph", 15, "Uh oh"
                                )
                            )) }
                            composable(Screen.MapsScreen.route) {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HourlyScreen(hourlies: List<HourForecast>) {
    LazyColumn {
        items(hourlies) { hourly ->
            HourlyCell(forecast = hourly)
        }
    }
}

@Composable
fun HourlyCell(forecast: HourForecast) {

}

@Composable
fun DailyScreenUI(dailies: List<DayForecast>) {
    LazyColumn {
        items(dailies) { daily ->
            DailyCell(daily)
        }
    }
}

@Composable
fun DailyCell(forecast: DayForecast) {

}

@Composable
fun BottomBar(navHostController: NavHostController, items: List<Screen>) {
    BottomNavigation {
        val backStackEntry by navHostController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "") },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navHostController.navigate(screen.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BlueSkiesWeatherTheme {

    }
}