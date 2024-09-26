package com.example.presentation_word_of_day.words_of_day

import com.example.domain.repository.RemoteWordOfDayRepository
import com.example.domain.usecase.GetWordOfDayListUseCase
import com.example.domain.usecase.UseCase
import dagger.Module
import dagger.Provides

@Module
class WordsOfDayViewModelModule {

    @Provides
    fun provideGetWordOfDayListUseCase(
        repositoryWordOfDayRepository: RemoteWordOfDayRepository,
        configuration: UseCase.Configuration
    ): GetWordOfDayListUseCase = GetWordOfDayListUseCase(
        remoteRepository = repositoryWordOfDayRepository,
        configuration = configuration
    )

    @Provides
    fun provideWordsOfDayConverter(): WordsOfDayConverter =
        WordsOfDayConverter()
}