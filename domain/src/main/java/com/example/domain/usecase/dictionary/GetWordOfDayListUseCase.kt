package com.example.domain.usecase.dictionary

import com.example.domain.entity.dictionary.WordOfDay
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWordOfDayListUseCase(
    private val remoteRepository: RemoteWordOfDayRepository,
    configuration: Configuration
): UseCase<GetWordOfDayListUseCase.Request, GetWordOfDayListUseCase.Response>(configuration) {
    override fun process(request: Request): Flow<Response> =
        remoteRepository.getWordOfDayList(request.srcLangIso)
            .map {
                Response(it)
            }

    data class Request(val srcLangIso: String): UseCase.Request
    data class Response(val wordOfDayList: List<WordOfDay>): UseCase.Response
}