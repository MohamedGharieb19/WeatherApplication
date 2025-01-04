package com.gharieb.weather.presentation

import android.content.Context
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.use_case.GetWeatherUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var locationTrackerUseCase: GetLocationTrackerUseCase

    @Mock
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Mock
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var viewModel: WeatherViewModel
    private lateinit var context: Context

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        context = Mockito.mock(Context::class.java)
        viewModel = WeatherViewModel(
            locationTrackerUseCase = locationTrackerUseCase,
            getWeatherUseCase = getWeatherUseCase,
            sharedPreferencesHelper = sharedPreferencesHelper,
            context = context,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `currentWeatherInfo fetches weather by city`() = runTest {
        val city = "Cairo"
        val weather = weather
        Mockito.`when`(getWeatherUseCase.invoke(city = city)).thenReturn(Resource.Success(weather))

        viewModel.currentWeatherInfo(city = city)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(weather, viewModel.state.weatherInfo)
        assertEquals(city, viewModel.state.cityNameBySearch)
    }

    @Test
    fun `currentWeatherInfo fetches saved weather if available`() = runTest {
        val weather = weather
        Mockito.`when`(sharedPreferencesHelper.getHasUserSearchedCity(context)).thenReturn(true)
        Mockito.`when`(getWeatherUseCase.invoke()).thenReturn(Resource.Success(weather))

        viewModel.currentWeatherInfo()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(weather, viewModel.state.weatherInfo)
    }

    @Test
    fun `currentWeatherInfo fetches weather by location when no saved weather`() = runTest {
        val weather = weather
        val mockedLocation = Mockito.mock(Location::class.java)
        Mockito.`when`(mockedLocation.latitude).thenReturn(30.0)
        Mockito.`when`(mockedLocation.longitude).thenReturn(31.0)
        Mockito.`when`(sharedPreferencesHelper.getHasUserSearchedCity(context)).thenReturn(false)
        Mockito.`when`(locationTrackerUseCase.invoke()).thenReturn(mockedLocation)
        Mockito.`when`(getWeatherUseCase.invoke(lat = 30.0, long = 31.0)).thenReturn(Resource.Success(weather))

        viewModel.currentWeatherInfo()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(weather, viewModel.state.weatherInfo)
    }

    @Test
    fun `currentWeatherInfo handles error when location fails`() = runTest {
        Mockito.`when`(sharedPreferencesHelper.getHasUserSearchedCity(context)).thenReturn(false)
        Mockito.`when`(locationTrackerUseCase.invoke()).thenReturn(null)

        viewModel.currentWeatherInfo()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertTrue(viewModel.state.errorMessage!!.contains("Failed to retrieve location"))
    }

    @Test
    fun `handleResult updates state on success`() = runTest {
        val weather = weather
        val resource = Resource.Success(weather)

        viewModel.handleResult(resource, city = "Cairo")

        assertEquals(weather, viewModel.state.weatherInfo)
        assertEquals("Cairo", viewModel.state.cityNameBySearch)
        assertEquals(false, viewModel.state.isLoading)
        assertEquals(null, viewModel.state.errorMessage)
    }

    @Test
    fun `handleResult updates state on error`() = runTest {
        val resource = Resource.Error<Weather>("An error occurred")

        viewModel.handleResult(resource, city = null)

        assertEquals(null, viewModel.state.weatherInfo)
        assertEquals(false, viewModel.state.isLoading)
        assertEquals("An error occurred", viewModel.state.errorMessage)
    }
}


val weather = Weather(
    current = Current(
        lastUpdated = "Today: 02:00 PM", // Adjust based on `formatDateToToday` function from myLibrary com.github.MohamedGharieb19:WeatherUtilsLibrary:1.0.3
        isDay = true,
        temperature = "25.0°C",
        condition = Condition(
            code = 1000,
            icon = "https://icon.png",
            text = "Sunny"
        )
    ),
    location = com.gharieb.core.domain.entity.Location(
        localtime = "2025-01-04 14:00",
        name = "Cairo Egypt",
        latitude = "30.0",
        longitude = "31.0"
    )
)