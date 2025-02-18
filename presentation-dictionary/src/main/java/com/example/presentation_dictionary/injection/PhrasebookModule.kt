package com.example.presentation_dictionary.injection

import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.phrasebook.GetTopicListUseCase
import dagger.Module
import dagger.Provides

@Module
class PhrasebookModule {
    @DictionaryScope
    @Provides
    fun provideGetTopicListUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetTopicListUseCase = GetTopicListUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )
}