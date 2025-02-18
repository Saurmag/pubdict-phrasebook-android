package com.example.domain.usecase.phrasebook

import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTopicListUseCase(
    private val remoteRepository: RemotePhrasebookRepository,
    configuration: Configuration
): UseCase<GetTopicListUseCase.Request, GetTopicListUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getTopicList(
            srcLangIso = request.srcLangIso,
            tarLangIso = request.tarLangIso
        ).map {
            Response(it)
        }

    data class Request(val srcLangIso: String, val tarLangIso: String) : UseCase.Request
    data class Response(val topicList: List<Topic>) : UseCase.Response
}