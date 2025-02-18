package com.example.data_repository.repository.remote

import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.domain.entity.dictionary.WordOfDay
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteWordOfDayRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteWordOfDayDataSource
): RemoteWordOfDayRepository {
    override fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>> =
        remoteDataSource.getWordOfDayList(srcLangIso = srcLangIso)
}