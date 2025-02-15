package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.fetchAllFoodModel.FetchFoodResponse

interface FetchAllFoodRepository {
    suspend fun fetchAllFoods(): FetchFoodResponse
}
