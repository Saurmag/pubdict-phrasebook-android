package com.example.data_repository.datasource.local

import com.example.domain.entity.phrasebook.TranslationLanguage
import kotlinx.coroutines.flow.Flow

interface LocalTranslationLanguageDataSource {

    fun getTranslationLanguage(): Flow<TranslationLanguage>

    suspend fun updateTranslationLanguage(languageIso: String)
}