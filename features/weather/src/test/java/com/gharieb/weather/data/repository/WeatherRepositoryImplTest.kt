package com.gharieb.weather.data.repository

import android.content.Context
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.repository.local.IWeatherLocal
import com.gharieb.weather.domain.repository.remote.IWeatherRemote
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {

    @Mock
    private lateinit var remote: IWeatherRemote

    @Mock
    private lateinit var local: IWeatherLocal

    @Mock
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var weatherRepository: WeatherRepositoryImpl

    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = mock(Context::class.java)
        weatherRepository = WeatherRepositoryImpl(remote, local, sharedPreferencesHelper, context)
    }

    @Test
    fun `getWeather fetches data from remote when city is provided`() = runTest {
        val city = "Cairo"
        val weather = Weather(
            id = 1,
            current = Current(
                lastUpdated = "",
                isDay = false,
                temperature = "",
                condition = Condition(
                    icon = "",
                    text = "",
                    code = 0
                )
            ),
            location = Location(
                localtime = "",
                longitude = "",
                latitude = "",
                name = ""
            )
        )
        `when`(remote.getCurrentWeatherData(city = city)).thenReturn(Resource.Success(weather))

        val result = weatherRepository.getWeather(city = city)

        assertTrue(result is Resource.Success)
        assertEquals(weather, (result as Resource.Success).data)
        verify(local).saveWeather(weather)
    }

    @Test
    fun `getWeather fetches saved weather when no city is provided and saved data exists`() = runTest {
        val weather = Weather(
            id = 1,
            current = Current(
                lastUpdated = "",
                isDay = false,
                temperature = "",
                condition = Condition(
                    icon = "",
                    text = "",
                    code = 0
                )
            ),
            location = Location(
                localtime = "",
                longitude = "",
                latitude = "",
                name = ""
            )
        )
        `when`(sharedPreferencesHelper.getHasUserSearchedCity(context)).thenReturn(true)
        `when`(local.getSavedWeather()).thenReturn(Resource.Success(weather))

        val result = weatherRepository.getWeather()

        assertTrue(result is Resource.Success)
        assertEquals(weather, (result as Resource.Success).data)
    }

    @Test
    fun `getWeather fetches data from remote when no city is provided and no saved data exists`() = runTest {
        val weather = Weather(
            id = 1,
            current = Current(
                lastUpdated = "",
                isDay = false,
                temperature = "",
                condition = Condition(
                    icon = "",
                    text = "",
                    code = 0
                )
            ),
            location = Location(
                localtime = "",
                longitude = "",
                latitude = "",
                name = ""
            )
        )
        `when`(sharedPreferencesHelper.getHasUserSearchedCity(context)).thenReturn(false)
        `when`(remote.getCurrentWeatherData(lat = 30.0, long = 31.0)).thenReturn(Resource.Success(weather))

        val result = weatherRepository.getWeather(lat = 30.0, long = 31.0, city = null)

        assertTrue(result is Resource.Success)
        assertEquals(weather, (result as Resource.Success).data)
    }
}