package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import com.rhymezxcode.food.data.model.updateFood.UpdateFoodRequest
import javax.inject.Inject

class UpdateFoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : UpdateFoodRepository {
    override suspend fun updateFood(
     foodRequest: UpdateFoodRequest?
    ) = apiService.updateFood(
        foodRequest = foodRequest
    )
}
