package com.example.publicdictionary.network.model.phrase

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Translation(
    val id: Int,
    val translation: String,
    @Json(name = "phonetics_en") val enPhonetic: String?,
    @Json(name = "phonetics_ipa") val ipaPhonetic: String?
)
