package com.playground.blueskiesweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.playground.blueskiesweather.model.CurrentConditions
import com.playground.blueskiesweather.model.DayForecast
import com.playground.blueskiesweather.model.HourForecast
import com.playground.blueskiesweather.ui.theme.BlueSkiesWeatherTheme
import com.playground.blueskiesweather.vm.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueSkiesWeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val viewModel: ForecastViewModel by viewModels()
//                    viewModel.getForecast()
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController, listOf(Screen.DailyScreen, Screen.HourlyScreen))
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            Screen.DailyScreen.route,
                            Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.DailyScreen.route) { DailyScreenUI(dailies = listOf(
                                DayForecast(
                                70, 30, "Mar 14", "Clear", "", "", 10,
                                "Cloudy with a chance of meatballs."
                            )
                            )) }
                            composable(Screen.HourlyScreen.route) { HourlyScreen(hourlies = listOf(
                                HourForecast(
                                    40, "Cloudy", "1PM", "", "NE 10mph", 15, "Uh oh"
                                )
                            )) }
                            composable(Screen.CurrentConditionsScreen.route) {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentConditionsScreen(currentConditions: CurrentConditions) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_sunny),
            contentDescription = "forecast icon",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(Color.Yellow)
        )
        Text(text = currentConditions.temp.toString())

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
    Column {
        Text(text = forecast.time)
        Text(text = forecast.temp.toString())
        Image(
            painter = painterResource(id = R.drawable.ic_cloud),
            contentDescription = "forecast icon",
            modifier = Modifier.size(24.dp)
        )
        Text(text = forecast.conditions)
        Text(text = forecast.precipitationChance.toString())
        Text(text = forecast.wind)
    }
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = forecast.date)
        Text(text = "${forecast.highTemp} / ${forecast.lowTemp}")
        Image(
            painter = painterResource(id = R.drawable.ic_cloud),
            contentDescription = "forecast icon",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(Color.Blue)
        )
        Text(text = forecast.conditions)
        Text(text = forecast.precipitationChance.toString())
    }
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