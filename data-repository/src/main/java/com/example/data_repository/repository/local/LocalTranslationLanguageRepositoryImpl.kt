package com.example.data_repository.repository.local

import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.data_repository.repository.RepositoryConfiguration
import com.example.domain.entity.dictionary.Language
import com.example.domain.repository.local.LocalTranslationLanguageRepository
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