package com.example.data_remote.network.model.phrasebook

import com.example.data_remote.network.model.category.NetworkLanguage
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhrasebook(
    val id: Int,
    val title: String,
    val isPublished: Boolean,
    val originNetworkLanguage: NetworkLanguage,
    val translatedNetworkLanguageList: List<NetworkLanguage>
)
