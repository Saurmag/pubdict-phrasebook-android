package com.example.presentation_dictionary.word.single

import androidx.compose.runtime.Immutable
import com.example.domain.entity.dictionary.Word

@Immutable
data class DetailWordModel(
    val text: String,
    val translation: String,
    val ipaTransliteration: String,
    val enTransliteration: String
)

internal fun Word.mapToDetailWordModel() =
    DetailWordModel(
        text =  this.text,
        translation = this.translate,
        ipaTransliteration = this.ipaTransliteration,
        enTransliteration = this.enTransliteration
    )