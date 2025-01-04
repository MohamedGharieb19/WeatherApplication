package com.gharieb.weather.data.repository.local

import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.gharieb.core.utils.Resource
import com.gharieb.weather.data.source.local.WeatherDao
import com.gharieb.weather.domain.entity.Weather
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherLocalImplTest {

    private lateinit var weatherLocal: WeatherLocalImpl

    @Mock
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherLocal = WeatherLocalImpl(weatherDao)
    }

    @Test
    fun `saveWeather calls insertWeather with correct data`() = runTest {
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

        weatherLocal.saveWeather(weather)

        verify(weatherDao).insertWeather(weather)
    }

    @Test
    fun `getSavedWeather returns success when data exists`() = runTest {
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
        `when`(weatherDao.getWeather()).thenReturn(listOf(weather))

        val result = weatherLocal.getSavedWeather()

        assertTrue(result is Resource.Success)
        assertEquals(weather, (result as Resource.Success).data)
    }

    @Test
    fun `getSavedWeather returns error when exception is thrown`() = runTest {
        `when`(weatherDao.getWeather()).thenThrow(RuntimeException("Test exception"))

        val result = weatherLocal.getSavedWeather()

        assertTrue(result is Resource.Error)
        assertEquals("Test exception", (result as Resource.Error).message)
    }
}
