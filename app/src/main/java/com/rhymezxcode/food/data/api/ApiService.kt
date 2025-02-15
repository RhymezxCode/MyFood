package com.rhymezxcode.food.data.api

import com.rhymezxcode.food.data.model.createFood.CreateFoodRequest
import com.rhymezxcode.food.data.model.fetchAllFoodModel.FetchFoodResponse
import com.rhymezxcode.food.data.model.fetchOneFoodModel.FetchOneFoodResponse
import com.rhymezxcode.food.data.model.updateFood.UpdateFoodRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodApiService {
    @GET("foods")
    suspend fun getAllFoods(): FetchFoodResponse

    @GET("foods/{food_id}")
    suspend fun getOneFood(
        @Path("food_id") foodId: Int?,
    ): FetchOneFoodResponse

    @Multipart
    @POST("foods")
    suspend fun createFood(
        @Body foodRequest: CreateFoodRequest?
    ): Response<ResponseBody>

    @Multipart
    @POST("foods/{category_id}")
    suspend fun updateFood(
        @Body foodRequest: UpdateFoodRequest?
    ): Response<ResponseBody>
}
