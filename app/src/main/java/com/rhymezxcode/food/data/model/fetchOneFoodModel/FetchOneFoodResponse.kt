package com.rhymezxcode.food.data.model.fetchOneFoodModel

import com.squareup.moshi.Json

data class FetchOneFoodResponse(
    @Json(name = "data")
    val `data`: OneFoodItem?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?
)
