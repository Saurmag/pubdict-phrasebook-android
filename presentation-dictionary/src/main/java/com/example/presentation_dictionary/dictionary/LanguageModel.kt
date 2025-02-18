package com.example.presentation_dictionary.dictionary

import androidx.compose.runtime.Immutable
import com.example.domain.entity.dictionary.Language

@Immutable
data class LanguageModel(
    val id: Int,
    val title: String,
    val iso: String
)

internal fun Language.mapToLanguageModel() =
    LanguageModel(
        id = this.id,
        title = this.title,
        iso = this.iso
    )