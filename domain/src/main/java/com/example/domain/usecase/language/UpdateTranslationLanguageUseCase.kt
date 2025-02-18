package com.example.domain.usecase.language

import com.example.domain.entity.dictionary.Language
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UpdateTranslationLanguageUseCase(
    private val localRepository: LocalTranslationLanguageRepository,
    configuration: Configuration
): UseCase<UpdateTranslationLanguageUseCase.Request, UpdateTranslationLanguageUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        localRepository.updateTranslationLanguage(request.language)
        return flowOf(Response)
    }

    data class Request(val language: Language) : UseCase.Request
    object Response : UseCase.Response
}