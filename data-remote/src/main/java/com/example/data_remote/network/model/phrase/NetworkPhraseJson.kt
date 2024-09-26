package com.example.data_remote.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhraseJson(
    val id: Int,
    @Json(name = "category_id") val categoryId: Int,
    @Json(name = "translations") val translationList: List<Translation>
)
