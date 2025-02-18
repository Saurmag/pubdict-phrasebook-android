package com.example.presentation_dictionary.dictionary

import androidx.compose.runtime.Immutable

@Immutable
data class TranslationLanguagesUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val languages: List<LanguageModel> = emptyList()
)
