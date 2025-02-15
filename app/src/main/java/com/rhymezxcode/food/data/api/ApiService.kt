package com.rhymezxcode.food.data.api

import com.rhymezxcode.food.data.model.fetchAllFoodModel.FetchFoodResponse
import com.rhymezxcode.food.data.model.fetchOneFoodModel.FetchOneFoodResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {
    @GET("foods")
    suspend fun getAllFoods(): FetchFoodResponse

    @GET("foods/{food_id}")
    suspend fun getOneFood(
        @Path("food_id") foodId: Int?,
    ): FetchOneFoodResponse
}
