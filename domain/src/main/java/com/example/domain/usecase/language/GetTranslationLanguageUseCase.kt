package com.example.domain.usecase.language

import com.example.domain.entity.dictionary.Language
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.usecase.UseCase
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
    data class Response(val translationLanguage: Language): UseCase.Response
}