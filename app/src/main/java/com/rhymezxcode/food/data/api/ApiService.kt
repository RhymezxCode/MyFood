package com.rhymezxcode.food.data.api

import com.rhymezxcode.food.data.model.FoodItem
import retrofit2.http.GET

interface FoodApiService {
    @GET("foods") // Change the endpoint based on your API
    suspend fun getAllFoods(): List<FoodItem>
}
