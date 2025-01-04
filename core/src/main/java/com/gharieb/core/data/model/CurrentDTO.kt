package com.gharieb.core.data.model

import com.squareup.moshi.Json

data class CurrentDTO(
    @field:Json(name = "last_updated") val lastUpdated: String? = null,
    @field:Json(name = "is_day") val isDay: Int? = null,
    @field:Json(name = "temp_c") val temperature: String? = null,
    val condition: ConditionDTO? = null,
)
