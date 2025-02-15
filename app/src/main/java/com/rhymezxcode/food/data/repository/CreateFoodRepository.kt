package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.createFood.CreateFoodRequest
import okhttp3.ResponseBody
import retrofit2.Response

interface CreateFoodRepository {
    suspend fun createFood(
        foodRequest: CreateFoodRequest?
    ): Response<ResponseBody>
}
