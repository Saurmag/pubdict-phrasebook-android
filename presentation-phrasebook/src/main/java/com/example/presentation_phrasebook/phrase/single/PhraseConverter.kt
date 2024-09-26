package com.example.presentation_phrasebook.phrase.single

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.usecase.GetPhraseUseCase
import com.example.presentation_common.state.CommonResultConverter

class PhraseConverter : CommonResultConverter<GetPhraseUseCase.Response,
        PhraseUiState>() {
    override fun convertSuccess(data: GetPhraseUseCase.Response): PhraseUiState =
        PhraseUiState(
            phraseModel = data.phrase.mapToPhraseModel()
        )


    override fun convertError(exception: Throwable?): PhraseUiState =
        PhraseUiState(
            exception = exception
        )

}

private fun Phrase.mapToPhraseModel() =
    PhraseModel(
        id = this.id,
        originText = this.originText,
        translatedText = this.translatedText,
        ipaTextTransliteration = this.ipaTextTransliteration,
        enTextTransliteration = this.enTextTransliteration
    )