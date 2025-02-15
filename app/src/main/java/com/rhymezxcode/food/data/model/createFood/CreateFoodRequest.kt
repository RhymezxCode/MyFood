package com.rhymezxcode.food.data.model.createFood

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

data class CreateFoodRequest(
    @Part("name") val name: RequestBody,
    @Part("description") val description: RequestBody,
    @Part("category_id") val categoryId: RequestBody,
    @Part("calories") val calories: RequestBody,
    @Part("tags") val tags: List<MultipartBody.Part>?,
    @Part("images") val images: List<MultipartBody.Part>?
)
