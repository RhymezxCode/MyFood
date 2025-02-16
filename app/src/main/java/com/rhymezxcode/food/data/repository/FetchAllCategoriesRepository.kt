package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.categories.CategoriesResponse

interface FetchAllCategoriesRepository {
    suspend fun getAllCategories(): CategoriesResponse
}
