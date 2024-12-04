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
package com.example.weatherupdates.ui.viewmodels


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.domain.models.CurrentWeatherDomain
import com.example.weatherupdates.domain.usecases.GetCurrentWeatherUpdatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUpdatesUseCase: GetCurrentWeatherUpdatesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CurrentWeatherState())
    val state: State<CurrentWeatherState> = _state

    init {
        getCurrentWeatherUpdates()
    }

    private fun getCurrentWeatherUpdates() {
        getCurrentWeatherUpdatesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        currentWeather = result.data,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred",
                        currentWeather = null
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                        error = "",
                        currentWeather = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    data class CurrentWeatherState(
        val isLoading: Boolean = false,
        val error: String = "",
        val currentWeather: CurrentWeatherDomain? = null
    )
}

