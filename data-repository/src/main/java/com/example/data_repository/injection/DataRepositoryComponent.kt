package com.example.data_repository.injection

import com.example.data_repository.datasource.local.LocalTranslationLanguageDataSource
import com.example.data_repository.datasource.remote.RemoteDictionaryDataSource
import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import dagger.Component
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

    val remoteDictionaryRepository: RemoteDictionaryRepository

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
    val remoteDictionaryDataSource: RemoteDictionaryDataSource
}

interface LocalPhrasebookDataSourceDeps {
    val localTranslationLanguageDataSource: LocalTranslationLanguageDataSource
}