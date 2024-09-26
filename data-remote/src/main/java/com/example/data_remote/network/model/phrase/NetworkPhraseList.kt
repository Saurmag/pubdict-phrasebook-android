package com.example.data_remote.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhraseList(
    @Json(name = "results") val networkPhraseList: List<NetworkPhrase>
)
