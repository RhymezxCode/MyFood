package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import com.rhymezxcode.food.data.model.createFood.CreateFoodRequest
import javax.inject.Inject

class CreateFoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : CreateFoodRepository {
    override suspend fun createFood(
        foodRequest: CreateFoodRequest?
    ) = apiService.createFood(
        foodRequest = foodRequest
    )


}
