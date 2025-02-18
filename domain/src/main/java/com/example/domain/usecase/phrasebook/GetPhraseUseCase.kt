package com.example.domain.usecase.phrasebook

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPhraseUseCase(
    private val remoteRepository: RemotePhrasebookRepository,
    configuration: Configuration
): UseCase<GetPhraseUseCase.Request, GetPhraseUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getPhrase(request.phraseId)
            .map {
                Response(it)
            }

    data class Request(val phraseId: Int): UseCase.Request
    data class Response(val phrase: Phrase): UseCase.Response
}