package com.example.presentation_dictionary.dictionary

import androidx.compose.runtime.Immutable

@Immutable
data class TranslationLanguageUiState(
    val exception: Throwable? = null,
    val language: LanguageModel? = null
)
