package com.rhymezxcode.food.data.model.fetchAllFoodModel


import com.squareup.moshi.Json

data class Category(
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "updated_at")
    val updatedAt: String?
)
