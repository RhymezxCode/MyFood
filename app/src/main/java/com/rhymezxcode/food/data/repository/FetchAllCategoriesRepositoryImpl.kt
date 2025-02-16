package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import javax.inject.Inject

class FetchAllCategoriesRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : FetchAllCategoriesRepository {
    override suspend fun getAllCategories() = apiService.getAllCategories()
}
