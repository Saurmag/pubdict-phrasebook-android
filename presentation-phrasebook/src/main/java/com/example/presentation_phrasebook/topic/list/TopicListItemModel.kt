package com.example.presentation_phrasebook.topic.list

import androidx.compose.runtime.Stable

@Stable
data class TopicListItemModel(
    val id: Int,
    val originTitle: String,
    val translatedTitle: String,
    val countPhrase: Int
)
