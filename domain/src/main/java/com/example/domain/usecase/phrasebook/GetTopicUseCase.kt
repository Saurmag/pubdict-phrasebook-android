package com.example.domain.usecase.phrasebook

import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTopicUseCase(
    private val remoteRepository: RemotePhrasebookRepository,
    configuration: Configuration
): UseCase<GetTopicUseCase.Request, GetTopicUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getTopic(
            id = request.topicId,
            srcLangIso = request.srcLangIso,
            tarLangIso = request.tarLangIso
        ).map {
                Response(topic = it)
        }

    data class Request(val topicId: Int, val srcLangIso: String, val tarLangIso: String):
        UseCase.Request
    data class Response(val topic: Topic): UseCase.Response
}