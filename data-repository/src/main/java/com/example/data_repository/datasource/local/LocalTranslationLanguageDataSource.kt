package com.example.data_repository.datasource.local

import com.example.domain.entity.phrasebook.Language
import kotlinx.coroutines.flow.Flow

interface LocalTranslationLanguageDataSource {

    fun getTranslationLanguage(): Flow<Language>

    suspend fun updateTranslationLanguage(language: Language)
}