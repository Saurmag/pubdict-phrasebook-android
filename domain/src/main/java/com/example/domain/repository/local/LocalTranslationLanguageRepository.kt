package com.example.domain.repository.local

import com.example.domain.entity.dictionary.Language
import kotlinx.coroutines.flow.Flow

interface LocalTranslationLanguageRepository {

    fun getTranslationLanguage(): Flow<Language>

    fun updateTranslationLanguage(language: Language)
}