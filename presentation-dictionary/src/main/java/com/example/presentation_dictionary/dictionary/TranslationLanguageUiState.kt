package com.example.presentation_dictionary.dictionary

import androidx.compose.runtime.Immutable

@Immutable
data class TranslationLanguageUiState(
    val isSelected: Boolean = false,
    val exception: Throwable? = null,
    val language: LanguageModel? = null
)
