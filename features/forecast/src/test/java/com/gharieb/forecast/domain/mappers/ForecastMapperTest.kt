package com.gharieb.forecast.domain.mappers

import com.gharieb.core.data.model.ConditionDTO
import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO
import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.gharieb.forecast.data.model.DayDTO
import com.gharieb.forecast.data.model.ForecastDTO
import com.gharieb.forecast.data.model.ForecastDayItemDTO
import com.gharieb.forecast.data.model.ForecastListDTO
import com.gharieb.forecast.domain.entity.Day
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.entity.ForecastDayItem
import com.gharieb.forecast.domain.entity.ForecastList
import com.gharieb.forecast.domain.mapper.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test


class ForecastMapperTest {

    @Test
    fun `toModel maps ForecastDTO to Forecast correctly`() {
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
        val dayDTO = DayDTO(averageTemperature = "25.0", condition = conditionDTO)
        val forecastDayItemDTO = ForecastDayItemDTO(
            date = "2025-01-05",
            day = dayDTO
        )
        val forecastListDTO = ForecastListDTO(forecastDayList = listOf(forecastDayItemDTO))
        val forecastDTO = ForecastDTO(current = currentDTO, location = locationDTO, forecast = forecastListDTO)

        // Act
        val result = forecastDTO.toModel()

        // Assert
        val expected = Forecast(
            current = Current(
                lastUpdated = "Today: 02:00 PM",
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
            ),
            forecast = ForecastList(
                forecastDayList = listOf(
                    ForecastDayItem(
                        date = "Sunday",
                        isToday = true,
                        day = Day(
                            averageTemperature = "25.0°C",
                            condition = Condition(
                                code = 1000,
                                icon = "https://icon.png",
                                text = "Sunny"
                            )
                        )
                    )
                )
            )
        )
        assertEquals(expected, result)
    }
}
