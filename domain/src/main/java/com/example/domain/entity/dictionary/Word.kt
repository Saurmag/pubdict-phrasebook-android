package com.example.domain.entity.dictionary

data class Word(
    val id: Int,
    val text: String,
    val translate: String,
    val ipaTransliteration: String,
    val enTransliteration: String
)
