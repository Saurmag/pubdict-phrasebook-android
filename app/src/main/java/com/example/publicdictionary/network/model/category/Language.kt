package com.example.publicdictionary.network.model.category

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Language(
    val id: Int,
    val title: String,
    val iso: String
)
