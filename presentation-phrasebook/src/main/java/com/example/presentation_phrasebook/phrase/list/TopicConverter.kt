package com.example.presentation_phrasebook.phrase.list

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.usecase.GetTopicUseCase
import com.example.presentation_common.state.CommonResultConverter

class TopicConverter : CommonResultConverter<GetTopicUseCase.Response,
        TopicUiState>() {
    override fun convertSuccess(data: GetTopicUseCase.Response): TopicUiState =
        TopicUiState(
            topicModel = data.topic.mapToTopicModel()
        )


    override fun convertError(exception: Throwable?): TopicUiState =
        TopicUiState(
            exception = exception
        )
}

private fun Topic.mapToTopicModel() =
    TopicModel(
        id = this.id,
        originTitle = this.originTitle,
        translatedTitle = this.translatedTitle,
        phraseList = phraseList.map { it.mapToPhraseListItemModel() }
    )

private fun Phrase.mapToPhraseListItemModel() =
    PhraseListItemModel(
        id = this.id,
        originText = this.originText,
        translatedText = this.translatedText
    )
