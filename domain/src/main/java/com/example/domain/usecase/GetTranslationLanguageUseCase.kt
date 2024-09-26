package com.example.domain.usecase

import com.example.domain.entity.phrasebook.TranslationLanguage
import com.example.domain.repository.LocalTranslationLanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTranslationLanguageUseCase(
    private val localRepository: LocalTranslationLanguageRepository,
    configuration: Configuration
): UseCase<GetTranslationLanguageUseCase.Request, GetTranslationLanguageUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        localRepository.getTranslationLanguage()
            .map {
                Response(it)
            }

    object Request : UseCase.Request
    data class Response(val translationLanguage: TranslationLanguage): UseCase.Response
}