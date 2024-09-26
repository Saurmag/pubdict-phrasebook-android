package com.example.domain.entity.phrasebook

data class Phrase(
    val id: Int,
    val topicId: Int,
    val translatedText: String,
    val originText: String,
    val ipaTextTransliteration: String,
    val enTextTransliteration: String
)
