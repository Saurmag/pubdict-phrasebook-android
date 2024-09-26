package com.example.data_local.injection

import android.content.Context
import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.data_repository.injection.LocalPhrasebookDataSourceDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Builder
import javax.inject.Singleton

@Singleton
@Component(modules = [LocalDataSourceModule::class, PersistenceModule::class])
interface DataLocalComponent : LocalPhrasebookDataSourceDeps {
    override val localTranslationLanguageDataSource: LocalTranslationLanguageDataSource

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): DataLocalComponent
    }
}