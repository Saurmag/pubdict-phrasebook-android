package com.example.domain.usecase.dictionary

import com.example.domain.entity.dictionary.Word
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordListUseCase(
    private val remoteRepository: RemoteDictionaryRepository,
    configuration: Configuration
): UseCase<GetWordListUseCase.Request, GetWordListUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        remoteRepository.getWordList(
            srcLangIso = request.srcLangIso,
            tarLangIso = request.tarLangIso,
            query = request.query,
            limit = request.limit,
            offset = request.offset
        ).map { Response(it) }

    data class Request(
        val srcLangIso: String,
        val tarLangIso: String,
        val query: String = "",
        val limit: Int = 2000,
        val offset: Int = 0
    ): UseCase.Request

    data class Response(val wordList: List<Word>): UseCase.Response
}