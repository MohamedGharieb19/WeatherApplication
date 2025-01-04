package com.gharieb.weather.domain.mappers

import com.gharieb.core.data.model.ConditionDTO
import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO
import com.gharieb.weather.data.model.WeatherDTO
import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.mapper.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WeatherMapperTest {

    @Test
    fun `toModel maps WeatherDTO to Weather correctly`() {
        // Arrange
        val conditionDTO = ConditionDTO(code = 1000, icon = "//icon.png", text = "Sunny")
        val currentDTO = CurrentDTO(
            lastUpdated = "2025-01-04 14:00",
            isDay = 1,
            temperature = "25.0",
            condition = conditionDTO
        )
        val locationDTO = LocationDTO(
            localtime = "2025-01-04 14:00",
            name = "Cairo",
            country = "Egypt",
            latitude = "30.0",
            longitude = "31.0"
        )
        val weatherDTO = WeatherDTO(current = currentDTO, location = locationDTO)

        // Act
        val result = weatherDTO.toModel()

        // Assert
        val expected = Weather(
            current = Current(
                lastUpdated = "Today: 02:00 pm", // Adjust based on `formatDateToToday` function from myLibrary com.github.MohamedGharieb19:WeatherUtilsLibrary:1.0.1
                isDay = true,
                temperature = "25.0°C",
                condition = Condition(
                    code = 1000,
                    icon = "https://icon.png",
                    text = "Sunny"
                )
            ),
            location = Location(
                localtime = "2025-01-04 14:00",
                name = "Cairo Egypt",
                latitude = "30.0",
                longitude = "31.0"
            )
        )
        assertEquals(expected, result)
    }
}