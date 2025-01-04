package com.gharieb.core

import com.gharieb.core.constants.ApiConstants
import org.junit.Assert.assertEquals
import org.junit.Test

class ApiConstantsTest {

    @Test
    fun `verify BASE_URL is correct`() {
        assertEquals("https://api.weatherapi.com/", ApiConstants.BASE_URL)
    }

    @Test
    fun `verify API_KEY is correct`() {
        assertEquals("76fcfd8926e94c0cacc95619242911", ApiConstants.API_KEY)
    }

    @Test
    fun `verify CURRENT_WEATHER endpoint is correct`() {
        assertEquals("v1/current.json", ApiConstants.CURRENT_WEATHER)
    }

    @Test
    fun `verify FORECAST_WEATHER endpoint is correct`() {
        assertEquals("v1/forecast.json", ApiConstants.FORECAST_WEATHER)
    }
}