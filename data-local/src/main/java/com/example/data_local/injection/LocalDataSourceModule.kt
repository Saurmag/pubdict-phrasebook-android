package com.example.data_local.injection

import com.example.data_local.datasource.LocalTranslationLanguageDataSourceImpl
import com.example.data_repository.datasource.local.LocalPhrasebookDataSource
import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalTranslationLanguageDataSource(
        localTranslationLanguageDataSource: LocalTranslationLanguageDataSourceImpl
    ): LocalTranslationLanguageDataSource
}