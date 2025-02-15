package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import javax.inject.Inject

class FetchAllFoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : FetchAllFoodRepository {
    override suspend fun fetchAllFoods() = apiService.getAllFoods()
}
