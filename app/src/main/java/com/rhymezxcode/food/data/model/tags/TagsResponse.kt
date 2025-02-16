package com.rhymezxcode.food.data.model.tags

import com.squareup.moshi.Json

data class TagsResponse(
    @Json(name = "data")
    val `data`: List<Tags>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?
)
