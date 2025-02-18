package com.example.data_repository.injection

import com.example.data_repository.repository.local.LocalTranslationLanguageRepositoryImpl
import com.example.data_repository.repository.remote.RemoteDictionaryRepositoryImpl
import com.example.data_repository.repository.remote.RemotePhrasebookRepositoryImpl
import com.example.data_repository.repository.remote.RemoteWordOfDayRepositoryImpl
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun  bindRemotePhrasebookRepository(
        remotePhrasebookRepositoryImpl: RemotePhrasebookRepositoryImpl
    ): RemotePhrasebookRepository

    @Singleton
    @Binds
    abstract fun bindRemoteWordOfDayRepository(
        remoteWordOfDayRepositoryImpl: RemoteWordOfDayRepositoryImpl
    ): RemoteWordOfDayRepository

    @Singleton
    @Binds
    abstract fun bindLocalTranslationLanguageRepository(
        localTranslationLanguageRepositoryImpl: LocalTranslationLanguageRepositoryImpl
    ): LocalTranslationLanguageRepository

    @Singleton
    @Binds
    abstract fun bindRemoteDictionaryRepository(
        remoteDictionaryRepositoryImpl: RemoteDictionaryRepositoryImpl
    ): RemoteDictionaryRepository
}