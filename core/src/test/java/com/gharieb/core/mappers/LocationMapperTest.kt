package com.gharieb.core.mappers
import com.gharieb.core.domain.entity.Location
import com.gharieb.core.data.model.LocationDTO
import com.gharieb.core.domain.mapper.toModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LocationMapperTest {

    @Test
    fun `toModel maps LocationDTO to Location correctly`() {
        // Arrange
        val locationDTO = LocationDTO(
            localtime = "2025-01-04 14:00",
            name = "Cairo",
            country = "Egypt",
            longitude = 31.0.toString(),
            latitude = 30.0.toString()
        )

        // Act
        val result = locationDTO.toModel()

        // Assert
        val expected = Location(
            localtime = "2025-01-04 14:00",
            name = "Cairo Egypt",
            longitude = 31.0.toString(),
            latitude = 30.0.toString()
        )
        assertEquals(expected, result)
    }
}