package com.example.presentation_dictionary.injection

import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.phrasebook.GetTopicUseCase
import dagger.Module
import dagger.Provides

@Module
class TopicModule {
    @DictionaryScope
    @Provides
    fun provideGetTopicUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetTopicUseCase = GetTopicUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )
}