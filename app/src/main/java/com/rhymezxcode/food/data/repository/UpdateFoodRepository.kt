package com.rhymezxcode.food.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Part

interface UpdateFoodRepository {
    suspend fun updateFood(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>
    ): Response<ResponseBody>
}
