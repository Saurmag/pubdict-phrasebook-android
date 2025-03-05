package com.example.data_local.datasource

import androidx.datastore.core.DataStore
import com.example.data_local.TranslationLanguagePreferences
import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTranslationLanguageDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<TranslationLanguagePreferences>
): LocalTranslationLanguageDataSource{
    override fun getTranslationLanguage(): Flow<Language> {
        return dataStore.data.map {
            Language(
                id = it.id,
                title = it.title,
                iso = it.iso
            )
        }.catch {
            throw UseCaseException.LanguageException(it)
        }
    }

    override suspend fun updateTranslationLanguage(language: Language) {
        dataStore.updateData { currentTranLang ->
            currentTranLang.toBuilder()
                .setId(language.id)
                .setIso(language.iso)
                .setTitle(language.title)
                .build()
        }
    }
}