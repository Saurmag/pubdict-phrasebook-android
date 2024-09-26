package com.example.data_remote.network.model.category

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryReduced(
    val id: Int,
    @Json(name = "category_translations") val categoryTranslationList: List<CategoryTranslation>
)