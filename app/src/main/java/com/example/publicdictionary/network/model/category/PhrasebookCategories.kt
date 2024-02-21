package com.example.publicdictionary.network.model.category

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhrasebookCategories(
    @Json(name = "results") val categories: List<Category>
)
