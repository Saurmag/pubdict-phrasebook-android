package com.example.domain.entity.phrasebook

import com.example.domain.entity.dictionary.Language

data class Topic(
    val id: Int,
    val originTitle: String,
    val countPhrase: Int,
    val originLanguage: Language,
    val translatedLanguage: Language,
    val phraseList: List<Phrase>,
    val translatedTitle: String
)
