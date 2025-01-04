package com.gharieb.forecast.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import com.gharieb.core.utils.Resource
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.use_case.GetForecastWeatherDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val locationTrackerUseCase: GetLocationTrackerUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherDataUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(ForecastState())
        private set

    init {
        state = state.copy(
            cityNameBySearch = savedStateHandle.get<String>("city")
        )
    }

    fun handleIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.LoadCurrentWeather -> forecastInfo(days = intent.days)
        }
    }

    private fun forecastInfo(days: String) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )
            val result: Resource<Forecast> = state.cityNameBySearch?.let { nameCity ->
                getForecastWeatherUseCase.invoke(city = nameCity, days = days)
            } ?: run {
                locationTrackerUseCase.invoke()?.let { currentLocation ->
                    getForecastWeatherUseCase.invoke(
                        lat = currentLocation.latitude,
                        long = currentLocation.longitude,
                        days = days
                    )
                } ?: run {
                    getForecastWeatherUseCase.invoke(days = days)
                }
            }

            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        forecastInfo = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        forecastInfo = null,
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}