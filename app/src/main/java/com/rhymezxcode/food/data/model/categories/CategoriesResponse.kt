package com.rhymezxcode.food.data.model.categories


import com.squareup.moshi.Json

data class CategoriesResponse(
    @Json(name = "data")
    val `data`: List<Categories>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?
)
