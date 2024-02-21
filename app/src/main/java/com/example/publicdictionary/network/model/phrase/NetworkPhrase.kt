package com.example.publicdictionary.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhrase(
    val id: Int,
    @Json(name = "category_id") val idCategory: Int,
    val translations: List<Translation>
)
