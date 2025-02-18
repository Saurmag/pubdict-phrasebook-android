package com.example.presentation_dictionary.injection

import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.dictionary.GetWordListUseCase
import com.example.domain.usecase.dictionary.GetWordOfDayListUseCase
import com.example.domain.usecase.dictionary.GetWordUseCase
import com.example.domain.usecase.share.ShareTextFormatter
import com.example.domain.usecase.share.ShareTextUseCase
import com.example.domain.usecase.share.ShareTextUtil
import com.example.presentation_dictionary.word.single.DetailWordModel
import dagger.Module
import dagger.Provides

@Module
class WordsModule {

    @DictionaryScope
    @Provides
    fun provideGetWordUseCase(
        remoteDictionaryRepository: RemoteDictionaryRepository,
        configuration: UseCase.Configuration
    ): GetWordUseCase = GetWordUseCase(
        remoteRepository = remoteDictionaryRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideGetWordOfDayListUseCase(
        remoteWordOfDayRepository: RemoteWordOfDayRepository,
        configuration: UseCase.Configuration
    ): GetWordOfDayListUseCase = GetWordOfDayListUseCase(
        remoteRepository = remoteWordOfDayRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideGetWordListUseCase(
        remoteDictionaryRepository: RemoteDictionaryRepository,
        configuration: UseCase.Configuration
    ): GetWordListUseCase = GetWordListUseCase(
        remoteRepository = remoteDictionaryRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideShareTextUseCase(
        @DetailWordFormatter shareTextFormatter: ShareTextFormatter<DetailWordModel>,
        shareTextUtil: ShareTextUtil,
        @MainDispatcher configuration: UseCase.Configuration
    ): ShareTextUseCase<DetailWordModel> =
        ShareTextUseCase(
            shareTextFormatter = shareTextFormatter,
            shareTextUtil = shareTextUtil,
            configuration = configuration
        )
}