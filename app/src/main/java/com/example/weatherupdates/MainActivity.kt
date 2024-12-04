/*
 * Copyright 2024 Ezra Kanake.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.weatherupdates

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherupdates.core.formatHourAndMinute
import com.example.weatherupdates.ui.composables.WeatherCard
import com.example.weatherupdates.ui.composables.WeatherDataDisplay
import com.example.weatherupdates.ui.viewmodels.CurrentWeatherViewModel
import com.example.weatherupdates.ui.viewmodels.GetWeatherDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNav()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNav(
    navController: NavHostController = rememberNavController(),
    viewModel: GetWeatherDetailsViewModel = hiltViewModel()
) {
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var showCalendar by remember { mutableStateOf(true) }

    val state = viewModel.state.value

    LaunchedEffect(selectedDate) {
        selectedDate?.let {
            viewModel.fetchWeatherDetails(it)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.CalendarView.route
    ) {
        composable(route = Screen.CalendarView.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (showCalendar) {
                        CalendarView(
                            onDateSelected = { timestamp ->
                                selectedDate = timestamp?.let { convertTimestampToDate(it) }
                                showCalendar = false
                            },
                            onDismiss = {
                                showCalendar = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (selectedDate == null) {
                    CurrentWeatherScreen(navController)
                } else {
                    CalendarDetailsScreen(
                        date = selectedDate,
                        state = state,
                        navController = navController
                    )
                }
            }
        }

        composable(route = Screen.CurrentWeatherScreen.route) {
            CurrentWeatherScreen(navController)
        }

        composable(
            route = "${Screen.CalendarDetailsScreen.route}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            CalendarDetailsScreen(date = date, state = state, navController = navController)
        }
    }
}

fun convertTimestampToDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

sealed class Screen(val route: String) {
    object CurrentWeatherScreen : Screen("current_weather")
    object CalendarView : Screen("calendar")
    object CalendarDetailsScreen : Screen("calendar_details")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeatherScreen(
    navController: NavHostController,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    if (state.isLoading) {
        CircularProgressIndicator()
    } else if (state.error.isNotEmpty()) {
        Text(text = "Error: ${state.error}", color = Color.Red)
    } else {
        WeatherCard(
            state = state,
            backgroundColor = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDetailsScreen(
    date: String?,
    state: GetWeatherDetailsViewModel.WeatherDetailsState,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hourly Details") }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.error.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.error}", color = Color.Red)
            }
        } else {
            state.weatherDetails?.let { weather ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(weather.hour ?: emptyList()) { hour ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(text = "Today: ${formatHourAndMinute(hour.time)}")

                                    Text(
                                        text = "Temp: ${hour.temp_c}°C",
                                    )
                                    Text(text = "Condition: ${hour.condition.text}")
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        WeatherDataDisplay(
                                            value = hour.humidity,
                                            unit = "%",
                                            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                                            iconTint = Color.Blue,
                                            textStyle = TextStyle(color = Color.Black)
                                        )
                                        WeatherDataDisplay(
                                            value = hour.wind_kph.roundToInt(),
                                            unit = "km/h",
                                            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                                            iconTint = Color.Blue,
                                            textStyle = TextStyle(color = Color.Black)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




