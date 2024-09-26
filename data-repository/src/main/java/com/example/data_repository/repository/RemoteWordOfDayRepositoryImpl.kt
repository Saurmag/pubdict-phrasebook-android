package com.example.data_repository.repository

import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.domain.entity.WordOfDay
import com.example.domain.repository.RemoteWordOfDayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteWordOfDayRepositoryImpl @Inject constructor(
    private val remoteWordOfDayDataSource: RemoteWordOfDayDataSource
): RemoteWordOfDayRepository {
    override fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>> =
        remoteWordOfDayDataSource.getWordOfDayList(srcLangIso = srcLangIso)
}