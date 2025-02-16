package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CreateFoodRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : CreateFoodRepository {

    override suspend fun createFood(
        name: RequestBody,
        description: RequestBody,
        categoryId: RequestBody,
        calories: RequestBody,
        tags: List<MultipartBody.Part>,
        images: List<MultipartBody.Part>,
    ) = apiService.createFood(
        name = name,
        description = description,
        categoryId = categoryId,
        calories = calories,
        tags = tags,
        images = images,
    )

}
