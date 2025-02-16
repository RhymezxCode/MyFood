package com.rhymezxcode.food.data.repository

import com.rhymezxcode.food.data.model.tags.TagsResponse

interface FetchAllTagsRepository {
    suspend fun getAllTags(): TagsResponse
}
