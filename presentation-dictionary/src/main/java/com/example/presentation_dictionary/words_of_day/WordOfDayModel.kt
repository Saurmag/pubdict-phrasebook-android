package com.example.presentation_dictionary.words_of_day

import androidx.compose.runtime.Immutable
import com.example.domain.entity.dictionary.WordOfDay

@Immutable
data class WordOfDayModel(
    val id: Int,
    val word: String,
    val image: ImageWordOfDayModel
)

internal fun WordOfDay.mapToWordOfDayModel() =
    WordOfDayModel(
        id = this.id,
        word = this.word,
        image = this.image.mapToImageWordOfDayModel()
    )
