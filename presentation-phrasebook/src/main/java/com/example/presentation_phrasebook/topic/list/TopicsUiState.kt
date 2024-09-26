package com.example.presentation_phrasebook.topic.list

import com.example.presentation_common.state.UiState

data class TopicsUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val topicList: List<TopicListItemModel>? = null
) : UiState()
