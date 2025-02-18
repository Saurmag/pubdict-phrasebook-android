package com.example.presentation_dictionary.injection

import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.phrasebook.GetPhraseUseCase
import com.example.domain.usecase.share.ShareTextFormatter
import com.example.domain.usecase.share.ShareTextUseCase
import com.example.domain.usecase.share.ShareTextUtil
import com.example.presentation_dictionary.phrase.PhraseModel
import dagger.Module
import dagger.Provides

@Module
class PhraseModule {
    @DictionaryScope
    @Provides
    fun provideGetPhraseUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetPhraseUseCase = GetPhraseUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideShareTextUseCase(
        @PhraseFormatter shareTextFormatter: ShareTextFormatter<PhraseModel>,
        shareTextUtil: ShareTextUtil,
        @MainDispatcher configuration: UseCase.Configuration
    ): ShareTextUseCase<PhraseModel> =
        ShareTextUseCase(
            shareTextFormatter = shareTextFormatter,
            shareTextUtil = shareTextUtil,
            configuration = configuration
        )
}