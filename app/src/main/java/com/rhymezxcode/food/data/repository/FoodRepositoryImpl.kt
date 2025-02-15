package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import com.rhymezxcode.food.data.model.FoodItem
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService
) : FoodRepository {
    override suspend fun fetchAllFoods(): List<FoodItem> {
        return try {
            apiService.getAllFoods()
        } catch (e: Exception) {
            emptyList() // Return empty list in case of failure
        }
    }
}
