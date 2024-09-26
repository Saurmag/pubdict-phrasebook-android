package com.example.data_remote.network.model.wordofday

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkImageWordOfDay(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int
)
