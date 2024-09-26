package com.example.presentation_phrasebook.phrase.list

import com.example.presentation_common.state.UiState

data class TopicUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val topicModel: TopicModel? = null
) : UiState()
