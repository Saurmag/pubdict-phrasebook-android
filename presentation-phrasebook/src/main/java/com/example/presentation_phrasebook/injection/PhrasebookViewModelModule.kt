package com.example.presentation_phrasebook.injection

import com.example.domain.repository.RemotePhrasebookRepository
import com.example.domain.usecase.GetPhraseUseCase
import com.example.domain.usecase.GetTopicListUseCase
import com.example.domain.usecase.GetTopicUseCase
import com.example.domain.usecase.UseCase
import com.example.presentation_phrasebook.phrase.list.TopicConverter
import com.example.presentation_phrasebook.phrase.single.PhraseConverter
import com.example.presentation_phrasebook.topic.list.TopicListConverter
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class PhrasebookViewModelModule {

    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration =
        UseCase.Configuration(
            scope = CoroutineScope(context = Dispatchers.IO),
            dispatcher = Dispatchers.IO
        )

    @Provides
    fun providePhraseConverter(): PhraseConverter =
        PhraseConverter()

    @Provides
    fun provideGetPhraseUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetPhraseUseCase = GetPhraseUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )

    @Provides
    fun provideTopicConverter(): TopicConverter =
        TopicConverter()

    @Provides
    fun provideGetTopicConverter(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetTopicUseCase = GetTopicUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )

    @Provides
    fun provideTopicListConverter(): TopicListConverter =
        TopicListConverter()

    @Provides
    fun provideGetTopicListUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetTopicListUseCase = GetTopicListUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )
}