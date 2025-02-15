package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import javax.inject.Inject

class FetchOneFoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : FetchOneFoodRepository {
    override suspend fun fetchOneFood(
        id: Int?,
    ) = apiService.getOneFood(id)
}
