package com.example.publicdictionary.network.model.category

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryTranslation(
    val id: Int,
    val name: String,
    val language: Language
)
