package com.example.domain.entity

data class WordOfDay(
    val id: Int,
    val entryId: Int,
    val word: String,
    val languageIso: String,
    val ipaWordTransliteration: String,
    val enWordTransliteration: String,
    val image: ImageWordOfDay
)
