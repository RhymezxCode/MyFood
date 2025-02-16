package com.rhymezxcode.food.data.api

import com.rhymezxcode.food.data.model.categories.CategoriesResponse
import com.rhymezxcode.food.data.model.fetchAllFoodModel.FetchFoodResponse
import com.rhymezxcode.food.data.model.fetchOneFoodModel.FetchOneFoodResponse
import com.rhymezxcode.food.data.model.tags.TagsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FoodApiService {
    @GET("foods")
    suspend fun getAllFoods(): FetchFoodResponse

    @GET("tags")
    suspend fun getAllTags(): TagsResponse

    @GET("categories")
    suspend fun getAllCategories(): CategoriesResponse

    @GET("foods/{food_id}")
    suspend fun getOneFood(
        @Path("food_id") foodId: Int?,
    ): FetchOneFoodResponse

    @Multipart
    @POST("foods")
    suspend fun createFood(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>
    ): Response<ResponseBody>

    @Multipart
    @POST("foods/{category_id}")
    suspend fun updateFood(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>
    ): Response<ResponseBody>
}
