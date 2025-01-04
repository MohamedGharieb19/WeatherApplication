package com.gharieb.weather.data.repository.remote

import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO
import com.gharieb.core.utils.Resource
import com.gharieb.weather.data.model.WeatherDTO
import com.gharieb.weather.data.source.remote.WeatherApi
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherRemoteImplTest {

    @Mock
    private lateinit var weatherApi: WeatherApi

    private lateinit var weatherRemote: WeatherRemoteImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherRemote = WeatherRemoteImpl(weatherApi)
    }

    @Test
    fun `getCurrentWeatherData calls API with city name`() = runTest {
        val city = "Cairo"
        val weatherDTO = WeatherDTO(
            current = CurrentDTO(),
            location = LocationDTO()
        )
        `when`(weatherApi.getCurrentWeatherData(value = city)).thenReturn(weatherDTO)

        val result = weatherRemote.getCurrentWeatherData(city = city)

        assertTrue(result is Resource.Success)
        assertNotNull((result as Resource.Success).data)
    }

    @Test
    fun `getCurrentWeatherData calls API with latitude and longitude`() = runTest {
        val lat = 30.0
        val long = 31.0
        val weatherDTO = WeatherDTO(
            current = CurrentDTO(),
            location = LocationDTO()
        )
        `when`(weatherApi.getCurrentWeatherData(value = "$lat,$long")).thenReturn(weatherDTO)

        val result = weatherRemote.getCurrentWeatherData(lat = lat, long = long)

        assertTrue(result is Resource.Success)
        assertNotNull((result as Resource.Success).data)
    }

}

