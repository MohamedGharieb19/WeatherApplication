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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationTrackerUseCase: GetLocationTrackerUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun currentWeatherInfo(city: String? = null) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )

            val result: Resource<Weather> = city?.let { nameCity ->
                withContext(Dispatchers.IO) {
                    getWeatherUseCase.invoke(city = nameCity)
                }
            } ?: run {
                locationTrackerUseCase.invoke()?.let { currentLocation ->
                    withContext(Dispatchers.IO) {
                        getWeatherUseCase.invoke(
                            lat = currentLocation.latitude,
                            long = currentLocation.longitude
                        )
                    }
                } ?: run {
                    withContext(Dispatchers.IO) {
                        getWeatherUseCase.invoke()
                    }
                }
            }

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
    }

    private fun saveSearched(city: String) {
        sharedPreferencesHelper.setHasUserSearchedCity(context = context, hasSearchedCity = true)
        sharedPreferencesHelper.setSearchedCityName(context = context, cityName = city)
    }

    fun resetCity() {
        state = state.copy(
            cityNameBySearch = null
        )
    }

}