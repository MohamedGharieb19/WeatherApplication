package com.gharieb.forecast.domain.entity

import com.gharieb.core.domain.entity.Condition

data class Day(
    val averageTemperature: String,
    val condition: Condition?,
)