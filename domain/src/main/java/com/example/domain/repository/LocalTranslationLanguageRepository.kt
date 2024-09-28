package com.example.domain.repository

import com.example.domain.entity.phrasebook.Language
import kotlinx.coroutines.flow.Flow

interface LocalTranslationLanguageRepository {

    fun getTranslationLanguage(): Flow<Language>

    fun updateTranslationLanguage(language: Language)
}