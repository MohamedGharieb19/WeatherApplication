package com.gharieb.forecast.presentation

import androidx.lifecycle.SavedStateHandle
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import com.gharieb.core.utils.Resource
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.use_case.GetForecastWeatherDataUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {

    @Mock
    private lateinit var locationTrackerUseCase: GetLocationTrackerUseCase

    @Mock
    private lateinit var getForecastWeatherUseCase: GetForecastWeatherDataUseCase

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: ForecastViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        savedStateHandle = SavedStateHandle(mapOf("city" to "Cairo"))
        viewModel = ForecastViewModel(
            locationTrackerUseCase,
            getForecastWeatherUseCase,
            savedStateHandle,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init sets cityNameBySearch from savedStateHandle`() {
        assertEquals("Cairo", viewModel.state.cityNameBySearch)
    }

    @Test
    fun `forecastInfo fetches forecast using city from saved state`() = runTest {
        val days = "5"
        val forecast = Forecast(null, null, null)
        Mockito.`when`(getForecastWeatherUseCase.invoke(city = "Cairo", days = days))
            .thenReturn(Resource.Success(forecast))

        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(forecast, viewModel.state.forecastInfo)
    }

    @Test
    fun `forecastInfo fetches forecast using location if city is not provided`() = runTest {
        val days = "7"
        val forecast = Forecast(null, null, null)
        val location = Mockito.mock(android.location.Location::class.java).apply {
            Mockito.`when`(latitude).thenReturn(30.0)
            Mockito.`when`(longitude).thenReturn(31.0)
        }

        Mockito.`when`(locationTrackerUseCase.invoke()).thenReturn(location)
        Mockito.`when`(getForecastWeatherUseCase.invoke(lat = 30.0, long = 31.0, days = days))
            .thenReturn(Resource.Success(forecast))

        savedStateHandle["city"] = null
        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals(forecast, viewModel.state.forecastInfo)
    }

    @Test
    fun `forecastInfo handles location retrieval failure`() = runTest {
        val days = "3"
        Mockito.`when`(locationTrackerUseCase.invoke()).thenReturn(null)
        Mockito.`when`(getForecastWeatherUseCase.invoke(days = days))
            .thenReturn(Resource.Error("Location unavailable"))

        savedStateHandle["city"] = null
        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals("Location unavailable", viewModel.state.errorMessage)
    }

    @Test
    fun `forecastInfo handles API error`() = runTest {
        val days = "5"
        Mockito.`when`(getForecastWeatherUseCase.invoke(city = "Cairo", days = days))
            .thenReturn(Resource.Error("API Error"))

        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, viewModel.state.isLoading)
        assertEquals("API Error", viewModel.state.errorMessage)
    }
}