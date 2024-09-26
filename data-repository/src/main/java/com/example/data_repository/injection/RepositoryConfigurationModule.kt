package com.example.data_repository.injection

import com.example.data_repository.repository.RepositoryConfiguration
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RepositoryConfigurationModule {

    @Provides
    fun provideRepositoryDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideRepositoryScope(dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(context = dispatcher)
}
