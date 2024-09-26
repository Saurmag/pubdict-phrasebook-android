package com.example.presentation_word_of_day.words_of_day

import com.example.domain.entity.ImageWordOfDay
import com.example.domain.entity.WordOfDay
import com.example.domain.usecase.GetWordOfDayListUseCase
import com.example.presentation_common.state.CommonResultConverter

class WordsOfDayConverter : CommonResultConverter<GetWordOfDayListUseCase.Response,
        WordsOfDayUiState>() {
    override fun convertSuccess(data: GetWordOfDayListUseCase.Response): WordsOfDayUiState =
        WordsOfDayUiState(
            wordsOfDay = data.wordOfDayList.map { it.mapToWordOfDayModel() }
        )

    override fun convertError(exception: Throwable?): WordsOfDayUiState =
        WordsOfDayUiState(
            exception = exception
        )
}

private fun WordOfDay.mapToWordOfDayModel() =
    WordOfDayModel(
        id = this.id,
        word = this.word,
        image = this.image.mapToImageWordOfDayModel()
    )

private fun ImageWordOfDay.mapToImageWordOfDayModel() =
    ImageWordOfDayModel(
        url = this.url,
        width = this.width,
        height = this.height,
        size = this.size
    )