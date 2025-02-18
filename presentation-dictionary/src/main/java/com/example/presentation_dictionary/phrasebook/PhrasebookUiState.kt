package com.example.presentation_dictionary.phrasebook

import androidx.compose.runtime.Immutable

@Immutable
data class PhrasebookUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val topicList: List<PhrasebookItemModel> = emptyList()
)
