package com.example.presentation_phrasebook.phrase.list

import androidx.compose.runtime.Immutable

@Immutable
data class TopicModel(
    val id: Int,
    val originTitle: String,
    val translatedTitle: String,
    val phraseList: List<PhraseListItemModel>
)