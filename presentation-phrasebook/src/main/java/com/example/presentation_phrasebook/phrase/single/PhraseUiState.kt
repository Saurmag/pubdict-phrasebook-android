package com.example.presentation_phrasebook.phrase.single

import com.example.presentation_common.state.UiState

data class PhraseUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val phraseModel: PhraseModel? = null
): UiState()

