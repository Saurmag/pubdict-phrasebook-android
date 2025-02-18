package com.example.presentation_dictionary.word.list

import androidx.compose.runtime.Immutable
import com.example.domain.entity.dictionary.Word

@Immutable
data class WordModel(
    val originText: String
)

internal fun Word.mapToWordModel() =
    WordModel(originText = this.text)
