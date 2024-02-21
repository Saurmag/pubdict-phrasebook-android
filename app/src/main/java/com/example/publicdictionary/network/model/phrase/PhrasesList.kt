package com.example.publicdictionary.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhrasesList(
    @Json(name = "results") val phrasesList: List<NetworkPhrase>
)
