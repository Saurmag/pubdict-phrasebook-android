package com.example.data_local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.domain.entity.phrasebook.TranslationLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

internal val KEY_TRANSLATION_LANGUAGE = stringPreferencesKey("translation_language")

class LocalTranslationLanguageDataSourceImpl(
    private val dataStore: DataStore<Preferences>
): LocalTranslationLanguageDataSource{
    override fun getTranslationLanguage(): Flow<TranslationLanguage> {
        val localeLanguage = Locale.getDefault().isO3Language
        return dataStore.data.map {
            TranslationLanguage(languageIso = it[KEY_TRANSLATION_LANGUAGE] ?: localeLanguage)
        }
    }

    override suspend fun updateTranslationLanguage(languageIso: String) {
        dataStore.edit {
            it[KEY_TRANSLATION_LANGUAGE] = languageIso
        }
    }
}