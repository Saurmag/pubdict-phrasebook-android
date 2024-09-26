package com.example.domain.repository

import com.example.domain.entity.phrasebook.TranslationLanguage
import kotlinx.coroutines.flow.Flow

interface LocalTranslationLanguageRepository {

    fun getTranslationLanguage(): Flow<TranslationLanguage>

    fun updateTranslationLanguage(languageIso: String)
}