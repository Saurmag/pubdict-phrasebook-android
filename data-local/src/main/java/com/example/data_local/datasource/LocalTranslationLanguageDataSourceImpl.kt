package com.example.data_local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

internal val KEY_LANGUAGE_ID = intPreferencesKey("language_id")
internal val KEY_LANGUAGE_TITLE = stringPreferencesKey("language_title")
internal val KEY_LANGUAGE_ISO = stringPreferencesKey("language_iso")

class LocalTranslationLanguageDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): LocalTranslationLanguageDataSource{
    override fun getTranslationLanguage(): Flow<Language> {
        val localeLangIso = Locale.getDefault().isO3Language
        val localeLangTitle = buildString {
            append(Locale.getDefault().displayLanguage[0].uppercase())
            val lastIndex = Locale.getDefault().displayLanguage.lastIndex
            append(Locale.getDefault().displayLanguage.slice(1..lastIndex))
        }
        return dataStore.data.map {
            Language(
                id = it[KEY_LANGUAGE_ID] ?: 0,
                title = it[KEY_LANGUAGE_TITLE] ?: localeLangTitle,
                iso = it[KEY_LANGUAGE_ISO] ?: localeLangIso
            )
        }.catch {
            throw UseCaseException.LanguageException(it)
        }
    }

    override suspend fun updateTranslationLanguage(language: Language) {
        dataStore.edit {
            it[KEY_LANGUAGE_ID] = language.id
            it[KEY_LANGUAGE_TITLE] = language.title
            it[KEY_LANGUAGE_ISO] = language.iso
        }
    }
}