package com.rhymezxcode.food.data.model.fetchAllFoodModel


import com.squareup.moshi.Json

data class FoodItem(
    @Json(name = "calories")
    val calories: Int?,
    @Json(name = "category")
    val category: Category?,
    @Json(name = "category_id")
    val categoryId: Int?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "foodImages")
    val foodImages: List<FoodImage>?,
    @Json(name = "foodTags")
    val foodTags: List<String>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "updated_at")
    val updatedAt: String?
)
