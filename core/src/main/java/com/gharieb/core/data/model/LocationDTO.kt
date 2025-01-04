package com.gharieb.core.data.model

import com.squareup.moshi.Json

data class LocationDTO(
    val localtime: String? = null,
    val country: String? = null,
    val name: String? = null,
    val region: String? = null,
    @field:Json(name = "lon") val longitude: String? = null,
    @field:Json(name = "lat") val latitude: String? = null,
)
