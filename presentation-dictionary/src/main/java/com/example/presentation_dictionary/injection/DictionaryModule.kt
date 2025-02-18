package com.example.presentation_dictionary.injection

import android.content.Intent
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.UseCase
import com.example.domain.usecase.language.GetTranslateLanguageListUseCase
import com.example.domain.usecase.language.GetTranslationLanguageUseCase
import com.example.domain.usecase.language.UpdateTranslationLanguageUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
class DictionaryModule {

    @DictionaryScope
    @Provides
    fun provideIntent() = Intent()

    @DictionaryScope
    @Provides
    @MainDispatcher
    fun provideUseCaseMainDispatcherConfiguration(): UseCase.Configuration =
        UseCase.Configuration(
            scope = CoroutineScope(context = Dispatchers.Main),
            dispatcher = Dispatchers.Main
        )

    @DictionaryScope
    @Provides
    fun provideUseCaseConfiguration(): UseCase.Configuration =
        UseCase.Configuration(
            scope = CoroutineScope(context = Dispatchers.IO),
            dispatcher = Dispatchers.IO
        )

    @DictionaryScope
    @Provides
    fun provideUpdateTranslationLanguageUseCase(
        localRepository: LocalTranslationLanguageRepository,
        configuration: UseCase.Configuration
    ): UpdateTranslationLanguageUseCase = UpdateTranslationLanguageUseCase(
        localRepository = localRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideGetTranslationLanguageListUseCase(
        remotePhrasebookRepository: RemotePhrasebookRepository,
        configuration: UseCase.Configuration
    ): GetTranslateLanguageListUseCase = GetTranslateLanguageListUseCase(
        remoteRepository = remotePhrasebookRepository,
        configuration = configuration
    )

    @DictionaryScope
    @Provides
    fun provideGetTranslationLanguageUseCase(
        localRepository: LocalTranslationLanguageRepository,
        configuration: UseCase.Configuration
    ): GetTranslationLanguageUseCase = GetTranslationLanguageUseCase(
        localRepository = localRepository,
        configuration = configuration
    )
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher