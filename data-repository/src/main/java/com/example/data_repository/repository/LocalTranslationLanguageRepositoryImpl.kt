package com.example.data_repository.repository

import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.domain.entity.phrasebook.Language
import com.example.domain.repository.LocalTranslationLanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalTranslationLanguageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalTranslationLanguageDataSource,
    private val configuration: RepositoryConfiguration
): LocalTranslationLanguageRepository {
    override fun getTranslationLanguage(): Flow<Language> =
        localDataSource.getTranslationLanguage()

    override fun updateTranslationLanguage(language: Language) {
        configuration.scope.launch(context = configuration.dispatcher) {
            localDataSource.updateTranslationLanguage(language)
        }
    }

}