package com.example.data_repository.injection

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

private const val REPOSITORY_SCOPE = "REPOSITORY_SCOPE"

@Module
class RepositoryConfigurationModule {

    @Singleton
    @Provides
    fun provideRepositoryDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideRepositoryScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(context = dispatcher + CoroutineName(name = REPOSITORY_SCOPE))
}
