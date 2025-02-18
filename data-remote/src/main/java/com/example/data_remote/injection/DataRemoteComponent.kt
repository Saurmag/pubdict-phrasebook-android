package com.example.data_remote.injection

import com.example.data_repository.datasource.remote.RemoteDictionaryDataSource
import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.data_repository.injection.RemotePhrasebookDataSourceDeps
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RemoteDataSourceModule::class])
interface DataRemoteComponent : RemotePhrasebookDataSourceDeps {
    override val remotePhrasebookDataSource: RemotePhrasebookDataSource
    override val remoteWordOfDayDataSource: RemoteWordOfDayDataSource
    override val remoteDictionaryDataSource: RemoteDictionaryDataSource
}