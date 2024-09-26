package com.example.domain.entity.phrasebook

data class Topic(
    val id: Int,
    val originTitle: String,
    val countPhrase: Int,
    val originLanguage: Language,
    val translatedLanguage: Language,
    val phraseList: List<Phrase>,
    val translatedTitle: String
)
