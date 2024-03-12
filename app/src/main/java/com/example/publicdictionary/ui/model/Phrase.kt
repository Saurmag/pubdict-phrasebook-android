package com.example.publicdictionary.ui.model

data class Phrase(
    val id: Int,
    val topicId: Int,
    val text: String,
    val textTranslation: String,
    val ipaTextTransliteration: String,
    val enTextTransliteration: String
)
