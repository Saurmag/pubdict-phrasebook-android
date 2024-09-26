package com.example.presentation_word_of_day.words_of_day

import com.example.presentation_common.state.UiState

data class WordsOfDayUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val wordsOfDay: List<WordOfDayModel>? = null
): UiState()
