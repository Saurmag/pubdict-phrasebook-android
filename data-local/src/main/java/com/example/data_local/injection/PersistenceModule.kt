package com.example.data_local.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data_local.datasource.LocalTranslationLanguageDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideLocalTranslationLanguageDataSourceImpl(context: Context) =
        LocalTranslationLanguageDataSourceImpl(context.dataStore)
}