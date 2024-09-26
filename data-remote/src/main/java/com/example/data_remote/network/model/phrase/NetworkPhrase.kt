package com.example.data_remote.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhrase(
    val id: Int,
    val categoryId: Int,
    val translationOrigin: String,
    val translation: String,
    val phoneticIpa: String?,
    val phoneticEn: String?
)
