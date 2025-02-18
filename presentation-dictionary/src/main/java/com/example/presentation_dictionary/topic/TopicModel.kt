package com.example.presentation_dictionary.topic

import androidx.compose.runtime.Immutable
import com.example.domain.entity.phrasebook.Topic

@Immutable
data class TopicModel(
    val id: Int,
    val originTitle: String,
    val translatedTitle: String,
    val phraseList: List<TopicItemModel>
)

internal fun Topic.mapToTopicModel() =
    TopicModel(
        id = this.id,
        originTitle = this.originTitle,
        translatedTitle = this.translatedTitle,
        phraseList = phraseList.map { it.mapToTopicItemModel() }
    )