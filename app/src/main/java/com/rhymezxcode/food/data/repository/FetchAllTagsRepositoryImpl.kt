package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.api.FoodApiService
import javax.inject.Inject

class FetchAllTagsRepositoryImpl @Inject constructor(
    private val apiService: FoodApiService,
) : FetchAllTagsRepository {
    override suspend fun getAllTags() = apiService.getAllTags()
}
