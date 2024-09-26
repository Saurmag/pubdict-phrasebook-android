package com.example.data_remote.network.model.category

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryList(
    @Json(name = "results") val categoryList: List<Category>
)
