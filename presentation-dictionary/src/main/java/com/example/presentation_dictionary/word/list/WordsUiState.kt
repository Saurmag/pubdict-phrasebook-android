package com.example.presentation_dictionary.word.list

import androidx.compose.runtime.Immutable

@Immutable
data class WordsUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val words: List<WordModel> = emptyList()
)
