package com.example.domain.usecase

import com.example.domain.entity.phrasebook.TranslationLanguage
import com.example.domain.repository.LocalTranslationLanguageRepository
import com.example.domain.usecase.UseCase.Configuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UpdateTranslationLanguageUseCase(
    private val localRepository: LocalTranslationLanguageRepository,
    configuration: Configuration
): UseCase<UpdateTranslationLanguageUseCase.Request, UpdateTranslationLanguageUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> {
        localRepository.updateTranslationLanguage(request.languageIso)
        return flowOf(Response)
    }

    data class Request(val languageIso: String) : UseCase.Request
    object Response : UseCase.Response
}