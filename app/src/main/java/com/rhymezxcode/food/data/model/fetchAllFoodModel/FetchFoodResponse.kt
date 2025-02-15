package com.rhymezxcode.food.data.model.fetchAllFoodModel

import com.squareup.moshi.Json

data class FetchFoodResponse(
    @Json(name = "data")
    val `data`: List<FoodItem>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "status")
    val status: String?
)
