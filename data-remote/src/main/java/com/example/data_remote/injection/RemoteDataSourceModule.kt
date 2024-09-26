package com.example.data_remote.injection

import com.example.data_remote.source.RemotePhrasebookDataSourceImpl
import com.example.data_remote.source.RemoteWordOfDayDataSourceImpl
import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemotePhrasebookDataSource(
        remotePhrasebookDataSourceImpl: RemotePhrasebookDataSourceImpl
    ): RemotePhrasebookDataSource

    @Singleton
    @Binds
    abstract fun bindRemoteWordOfDayDataSource(
        remoteWordOfDayDataSourceImpl: RemoteWordOfDayDataSourceImpl
    ): RemoteWordOfDayDataSource
}