package com.example.data_repository.injection

import com.example.data_repository.repository.LocalTranslationLanguageRepositoryImpl
import com.example.data_repository.repository.RemotePhrasebookRepositoryImpl
import com.example.data_repository.repository.RemoteWordOfDayRepositoryImpl
import com.example.domain.repository.LocalTranslationLanguageRepository
import com.example.domain.repository.RemotePhrasebookRepository
import com.example.domain.repository.RemoteWordOfDayRepository
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
}