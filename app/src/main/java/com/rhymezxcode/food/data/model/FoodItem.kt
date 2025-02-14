package com.rhymezxcode.food.data.model

data class FoodItem(
    val id: Int,
    val name: String,
    val calories: Int,
    val description: String,
    val imageUrl: Int,
    val tags: List<String>,
)
