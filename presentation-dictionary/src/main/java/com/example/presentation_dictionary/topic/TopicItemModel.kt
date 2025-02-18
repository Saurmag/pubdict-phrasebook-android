package com.example.presentation_dictionary.topic

import androidx.compose.runtime.Stable
import com.example.domain.entity.phrasebook.Phrase

@Stable
data class TopicItemModel(
    val id: Int,
    val originText: String,
    val translatedText: String,
)

internal fun Phrase.mapToTopicItemModel() =
    TopicItemModel(
        id = this.id,
        originText = this.originText,
        translatedText = this.translatedText
    )
