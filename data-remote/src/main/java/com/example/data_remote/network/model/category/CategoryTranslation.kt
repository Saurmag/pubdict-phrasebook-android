package com.example.data_remote.network.model.category

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryTranslation(
    val id: Int,
    val name: String,
    @Json(name = "language") val networkLanguage: NetworkLanguage
)
