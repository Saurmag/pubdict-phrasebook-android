package com.example.presentation_phrasebook.phrase.single

import androidx.compose.runtime.Stable

@Stable
data class PhraseModel(
    val id: Int,
    val originText: String,
    val translatedText: String,
    val ipaTextTransliteration: String,
    val enTextTransliteration: String
)
