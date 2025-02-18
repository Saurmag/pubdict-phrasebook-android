package com.example.domain.usecase.dictionary

import com.example.domain.entity.dictionary.Word
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordUseCase(
    private val remoteRepository: RemoteDictionaryRepository,
    configuration: Configuration
): UseCase<GetWordUseCase.Request, GetWordUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getWord(
            srcLangIso = request.srcLangIso,
            tarLangIso = request.tarLangIso,
            word = request.word
        ).map { Response(it) }

    data class Request(
        val srcLangIso: String,
        val tarLangIso: String,
        val word: String = ""
    ): UseCase.Request

    data class Response(val word: Word): UseCase.Response
}