package com.example.weatherupdates.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherupdates.core.Resource
import com.example.weatherupdates.domain.models.WeatherDetailsDomain
import com.example.weatherupdates.domain.usecases.GetWeatherDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetWeatherDetailsViewModel @Inject constructor(
    private val getWeatherDetailsUseCase: GetWeatherDetailsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WeatherDetailsState())
    val state: State<WeatherDetailsState> = _state

    // Call the use case when the ViewModel is initialized
    fun fetchWeatherDetails(date: String) {
        getWeatherDetailsUseCase(date).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        weatherDetails = result.data,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred",
                        weatherDetails = null
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                        error = "",
                        weatherDetails = null
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    data class WeatherDetailsState(
        val isLoading: Boolean = false,
        val error: String = "",
        val weatherDetails: WeatherDetailsDomain? = null
    )
}
