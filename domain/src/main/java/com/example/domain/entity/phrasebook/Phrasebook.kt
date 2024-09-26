package com.example.domain.entity.phrasebook

data class Phrasebook(
    val id: Int,
    val title: String,
    val originLanguage: Language,
    val translatedLanguage: Language
)
