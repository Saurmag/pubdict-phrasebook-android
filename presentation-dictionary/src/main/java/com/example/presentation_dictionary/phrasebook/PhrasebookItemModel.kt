package com.example.presentation_dictionary.phrasebook

import androidx.compose.runtime.Immutable
import com.example.domain.entity.phrasebook.Topic

@Immutable
data class PhrasebookItemModel(
    val id: Int,
    val originTitle: String,
    val translatedTitle: String,
    val countPhrase: Int
)

internal fun Topic.mapToTopicListItemModel() =
    PhrasebookItemModel(
        id = this.id,
        originTitle = this.originTitle,
        translatedTitle = this.translatedTitle,
        countPhrase = this.countPhrase
    )
