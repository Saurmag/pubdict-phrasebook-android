package com.example.presentation_dictionary.phrase

import androidx.compose.runtime.Immutable
import com.example.domain.entity.phrasebook.Phrase

@Immutable
data class PhraseModel(
    val id: Int,
    val originText: String,
    val translatedText: String,
    val ipaTextTransliteration: String,
    val enTextTransliteration: String
)

internal fun Phrase.mapToPhraseModel() =
    PhraseModel(
        id = this.id,
        originText = this.originText,
        translatedText = this.translatedText,
        ipaTextTransliteration = this.ipaTextTransliteration,
        enTextTransliteration = this.enTextTransliteration
    )
