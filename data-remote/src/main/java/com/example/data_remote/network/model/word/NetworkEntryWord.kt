package com.example.data_remote.network.model.word

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWordTranslation(
    val id: Int,
    @Json(name = "word_raw") val word: String,
    @Json(name = "translation_raw") val translation: String,
    @Json(name = "phonetics_ipa") val ipaTransliteration: String,
    @Json(name = "phonetics_en") val enTransliteration: String
)

@JsonClass(generateAdapter = true)
data class NetworkWordTransliteration(
    @Json(name = "phonetics_ipa") val ipaTransliteration: String,
    @Json(name = "phonetics_en") val enTransliteration: String
)
