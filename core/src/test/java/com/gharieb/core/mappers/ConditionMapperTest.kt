package com.gharieb.core.mappers

import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.data.model.ConditionDTO
import com.gharieb.core.domain.mapper.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConditionMapperTest {

    @Test
    fun `toModel maps ConditionDTO to Condition correctly`() {
        // Arrange
        val conditionDTO = ConditionDTO(
            code = 1000,
            icon = "//icon.png",
            text = "Sunny"
        )

        // Act
        val result = conditionDTO.toModel()

        // Assert
        val expected = Condition(
            code = 1000,
            icon = "https://icon.png",
            text = "Sunny"
        )
        assertEquals(expected, result)
    }
}