package com.example.presentation_phrasebook.topic.list

import com.example.domain.entity.phrasebook.Topic
import com.example.domain.usecase.GetTopicListUseCase
import com.example.presentation_common.state.CommonResultConverter

class TopicListConverter : CommonResultConverter<GetTopicListUseCase.Response,
        TopicsUiState>() {
    override fun convertSuccess(data: GetTopicListUseCase.Response): TopicsUiState =
        TopicsUiState(
            topicList = data.topicList
                .map { it.mapToTopicListItemModel() }
        )

    override fun convertError(exception: Throwable?): TopicsUiState =
        TopicsUiState(
            exception = exception
        )
}

private fun Topic.mapToTopicListItemModel() =
    TopicListItemModel(
        id = this.id,
        originTitle = this.originTitle,
        translatedTitle = this.translatedTitle,
        countPhrase = this.countPhrase
    )