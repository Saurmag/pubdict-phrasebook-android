package com.example.presentation_dictionary.words_of_day

import androidx.compose.runtime.Immutable
import com.example.domain.entity.dictionary.ImageWordOfDay

@Immutable
data class ImageWordOfDayModel(
    val url: String,
    val width: Int,
    val height: Int,
    val size: Int
)

internal fun ImageWordOfDay.mapToImageWordOfDayModel() =
    ImageWordOfDayModel(
        url = this.url,
        width = this.width,
        height = this.height,
        size = this.size
    )