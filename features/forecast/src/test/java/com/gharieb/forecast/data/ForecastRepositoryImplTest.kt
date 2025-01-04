package com.gharieb.forecast.data

import androidx.lifecycle.SavedStateHandle
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import com.gharieb.core.utils.Resource
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.use_case.GetForecastWeatherDataUseCase
import com.gharieb.forecast.presentation.ForecastIntent
import com.gharieb.forecast.presentation.ForecastViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var locationTrackerUseCase: GetLocationTrackerUseCase

    @Mock
    private lateinit var getForecastWeatherDataUseCase: GetForecastWeatherDataUseCase

    private lateinit var viewModel: ForecastViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = SavedStateHandle(mapOf("city" to "Cairo"))
        viewModel = ForecastViewModel(
            locationTrackerUseCase = locationTrackerUseCase,
            getForecastWeatherUseCase = getForecastWeatherDataUseCase,
            savedStateHandle = savedStateHandle,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `forecastInfo fetches forecast using city from saved state`() = runTest {
        // Arrange
        val forecast = Forecast(
            current = null,
            location = null,
            forecast = null
        )
        Mockito.`when`(getForecastWeatherDataUseCase.invoke(city = "Cairo", days = "3"))
            .thenReturn(Resource.Success(forecast))

        // Act
        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days = "3"))
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.isLoading.not())
        assertEquals(forecast, viewModel.state.forecastInfo)
        assertNull(viewModel.state.errorMessage)
    }

    @Test
    fun `forecastInfo handles API error`() = runTest {
        // Arrange
        val errorMessage = "API Error"
        Mockito.`when`(getForecastWeatherDataUseCase.invoke(city = "Cairo", days = "3"))
            .thenReturn(Resource.Error(errorMessage))

        // Act
        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days = "3"))
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.isLoading.not())
        assertNull(viewModel.state.forecastInfo)
        assertEquals(errorMessage, viewModel.state.errorMessage)
    }
}