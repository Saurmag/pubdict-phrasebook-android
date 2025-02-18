package com.example.domain.usecase.phrasebook

import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPhrasebookUseCase(
    private val remoteRepository: RemotePhrasebookRepository,
    configuration: Configuration
): UseCase<GetPhrasebookUseCase.Request, GetPhrasebookUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getPhrasebook(
            id = request.phrasebookId,
            tarLangIso = request.tarLangIso
        )
            .map {
                Response(phrasebook = it)
            }

    data class Request(
        val phrasebookId: Int,
        val tarLangIso: String
    ): UseCase.Request
    data class Response(val phrasebook: Phrasebook): UseCase.Response
}