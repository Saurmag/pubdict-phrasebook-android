package com.example.data_repository.injection

import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.domain.repository.LocalTranslationLanguageRepository
import com.example.domain.repository.RemotePhrasebookRepository
import com.example.domain.repository.RemoteWordOfDayRepository
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(
    dependencies = [RemotePhrasebookDataSourceDeps::class, LocalPhrasebookDataSourceDeps::class],
    modules = [RepositoryModule::class, RepositoryConfigurationModule::class]
)
interface DataRepositoryComponent {

    val remotePhrasebookRepository: RemotePhrasebookRepository

    val remoteWordOfDayRepository: RemoteWordOfDayRepository

    val localTranslationLanguageRepository: LocalTranslationLanguageRepository

    @Component.Builder
    interface Builder {
        fun depsLocalPhrasebookDataSource(deps: LocalPhrasebookDataSourceDeps): Builder

        fun depsRemotePhrasebookDataSource(deps: RemotePhrasebookDataSourceDeps): Builder

        fun build(): DataRepositoryComponent
    }
}

interface RemotePhrasebookDataSourceDeps {
    val remotePhrasebookDataSource: RemotePhrasebookDataSource
    val remoteWordOfDayDataSource: RemoteWordOfDayDataSource
}

interface LocalPhrasebookDataSourceDeps {
    val localTranslationLanguageDataSource: LocalTranslationLanguageDataSource
}