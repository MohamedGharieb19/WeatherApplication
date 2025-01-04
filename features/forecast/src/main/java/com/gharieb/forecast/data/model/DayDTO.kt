package com.gharieb.forecast.data.model

import com.gharieb.core.data.model.ConditionDTO
import com.squareup.moshi.Json

data class DayDTO(
    @field:Json(name="avgtemp_c")
    val averageTemperature: String? = null,
    val condition: ConditionDTO? = null,
)
