package com.example.presentation_dictionary.words_of_day

import androidx.compose.runtime.Immutable

@Immutable
data class WordOfDayUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val wordsOfDay: List<WordOfDayModel> = emptyList()
)
