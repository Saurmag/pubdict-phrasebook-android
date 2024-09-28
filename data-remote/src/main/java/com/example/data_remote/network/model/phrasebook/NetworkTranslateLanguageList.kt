package com.example.data_remote.network.model.phrasebook

import com.example.data_remote.network.model.category.NetworkLanguage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkTranslateLanguageList(
    @Json(name = "translate_to_lang_ids") val networkTranslateLanguageList: List<NetworkLanguage>
)
