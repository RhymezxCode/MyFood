package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.updateFood.UpdateFoodRequest
import okhttp3.ResponseBody
import retrofit2.Response

interface UpdateFoodRepository {
    suspend fun updateFood(
        foodRequest: UpdateFoodRequest?
    ): Response<ResponseBody>
}
