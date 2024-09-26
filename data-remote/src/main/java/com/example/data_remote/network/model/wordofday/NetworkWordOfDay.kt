package com.example.data_remote.network.model.wordofday

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkWordOfDay(
    val id: Int ,
    @Json(name = "entry_id") val entryId: Int?,
    val word: String,
    @Json(name = "language") val languageIso: String,
    @Json(name = "phonetics_ipa") val ipaWordTransliteration: String?,
    @Json(name = "phonetics_en") val enWordTransliteration: String?,
    @Json(name = "mobile_image") val networkImage: NetworkImageWordOfDay?
)