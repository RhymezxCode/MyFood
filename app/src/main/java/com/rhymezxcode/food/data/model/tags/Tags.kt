package com.rhymezxcode.food.data.model.tags

import com.squareup.moshi.Json

data class Tags(
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "updated_at")
    val updatedAt: String?
)
