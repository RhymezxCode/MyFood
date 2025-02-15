package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.FoodItem

interface FoodRepository {
    suspend fun fetchAllFoods(): List<FoodItem>
}
