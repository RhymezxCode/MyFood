package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.fetchOneFoodModel.FetchOneFoodResponse

interface FetchOneFoodRepository {
    suspend fun fetchOneFood(
        id: Int?,
    ): FetchOneFoodResponse
}
