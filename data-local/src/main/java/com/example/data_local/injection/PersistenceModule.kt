package com.example.data_local.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data_local.TranslationLanguagePreferences
import com.example.data_local.datasource.LocalTranslationLanguageDataSourceImpl
import com.example.data_local.datasource.TranslationLanguagePreferencesSerializer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private val Context.dataStore:
        DataStore<TranslationLanguagePreferences> by dataStore(
    fileName = "translation_language.proto",
    serializer = TranslationLanguagePreferencesSerializer
)

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideLocalTranslationLanguageDataSourceImpl(context: Context) =
        LocalTranslationLanguageDataSourceImpl(
            context.dataStore
        )
}