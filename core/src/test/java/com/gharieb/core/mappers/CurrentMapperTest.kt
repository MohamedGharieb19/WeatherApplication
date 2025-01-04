package com.gharieb.core.mappers

import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.data.model.ConditionDTO
import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.domain.mapper.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CurrentMapperTest {

    @Test
    fun `toModel maps CurrentDTO to Current correctly`() {
        // Arrange
        val conditionDTO = ConditionDTO(
            code = 1000,
            icon = "//icon.png", // get icon from api without https protocol
            text = "Sunny"
        )
        val currentDTO = CurrentDTO(
            lastUpdated = "2025-01-04 14:00",
            isDay = 1,
            temperature = "25.0", // add celcius to temperature
            condition = conditionDTO
        )

        // Act
        val result = currentDTO.toModel()

        // Assert
        val expected = Current(
            lastUpdated = "Today: 02:00 pm", // Adjust based on `formatDateToToday` function from myLibrary com.github.MohamedGharieb19:WeatherUtilsLibrary:1.0.1
            isDay = true,
            temperature = "25.0°C",
            condition = Condition(code = 1000, icon = "https://icon.png", text = "Sunny")
        )
        assertEquals(expected, result)
    }
}