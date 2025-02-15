package com.rhymezxcode.food.data.model.fetchAllFoodModel


import com.squareup.moshi.Json

data class FoodImage(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "image_url")
    val imageUrl: String?
)
