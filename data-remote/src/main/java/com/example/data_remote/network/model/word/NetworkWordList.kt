package com.example.data_remote.network.model.word

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWordList(
    @Json(name = "results") val networkWordList: List<NetworkWord>
)
