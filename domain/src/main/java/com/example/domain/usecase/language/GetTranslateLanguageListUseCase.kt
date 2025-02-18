package com.example.domain.usecase.language

import com.example.domain.entity.dictionary.Language
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTranslateLanguageListUseCase(
    private val remoteRepository: RemotePhrasebookRepository,
    configuration: Configuration
) : UseCase<GetTranslateLanguageListUseCase.Request, GetTranslateLanguageListUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getTranslateLanguageList(request.srcLangIso)
            .map { Response(it) }

    data class Request(val srcLangIso: String): UseCase.Request
    data class Response(val translateLanguageList: List<Language>): UseCase.Response
}