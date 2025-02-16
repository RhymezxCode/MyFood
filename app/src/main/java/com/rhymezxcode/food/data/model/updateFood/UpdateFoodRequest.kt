package com.rhymezxcode.food.data.model.updateFood

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

data class UpdateFoodRequest(
    val name: String,
    val description: String,
    val categoryId: String,
    val calories: String,
    val tags: List<String>,
    val images: List<File>
) {
    fun toRequestBodyParts(): Map<String, RequestBody> {
        return mapOf(
            "name" to name.toRequestBody(),
            "description" to description.toRequestBody(),
            "category_id" to categoryId.toRequestBody(),
            "calories" to calories.toRequestBody()
        )
    }

    fun toTagParts(): List<MultipartBody.Part> {
        return tags.mapIndexed { index, tag ->
            MultipartBody.Part.createFormData("tags[$index]", tag)
        }
    }

    fun toImageParts(): List<MultipartBody.Part> {
        return images.mapIndexed { index, image ->
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images[$index]", image.name, requestFile)
        }
    }
}
