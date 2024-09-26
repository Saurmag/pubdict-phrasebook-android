package com.example.data_remote.network.model.category

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkLanguage(
    val id: Int,
    val title: String,
    val iso: String
)
