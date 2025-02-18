package com.example.domain.entity.phrasebook

import com.example.domain.entity.dictionary.Language

data class Phrasebook(
    val id: Int,
    val title: String,
    val originLanguage: Language,
    val translatedLanguage: Language
)
