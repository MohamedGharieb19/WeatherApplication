package com.gharieb.weather.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.use_case.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTrackerUseCase: GetLocationTrackerUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(WeatherState())

    fun currentWeatherInfo(city: String? = null) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            val result: Resource<Weather> = if (!city.isNullOrEmpty()) {
                // Search by city name
                getWeatherUseCase.invoke(city = city)
            } else {
                // Check first is there is saved weather from search by city if not get location
                if (hasSavedWeather()) {
                    getWeatherUseCase.invoke()
                } else {
                    locationTrackerUseCase.invoke()?.let { currentLocation ->
                        getWeatherUseCase.invoke(
                            lat = currentLocation.latitude,
                            long = currentLocation.longitude
                        )
                    }
                        ?: Resource.Error("Failed to retrieve location. Please enable location services or search by city name")
                }

            }
            handleResult(result, city)
        }
    }

    private fun handleResult(result: Resource<Weather>, city: String?) {
        when (result) {
            is Resource.Success -> {
                city?.let {
                    // To pass it for Forecast days weather while navigate to Forecast Screen
                    // to prevent passing different value after getting city by search
                    saveSearched(city = city)
                    state = state.copy(
                        cityNameBySearch = city,
                        weatherInfo = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                } ?: run {
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }

            is Resource.Error -> {
                state = state.copy(
                    weatherInfo = null,
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }

    private fun saveSearched(city: String) {
        sharedPreferencesHelper.setHasUserSearchedCity(context = context, hasSearchedCity = true)
        sharedPreferencesHelper.setSearchedCityName(context = context, cityName = city)
    }

    fun hasSavedWeather(): Boolean {
        return sharedPreferencesHelper.getHasUserSearchedCity(context)
    }

    fun resetCity() {
        state = state.copy(
            cityNameBySearch = null
        )
    }
}