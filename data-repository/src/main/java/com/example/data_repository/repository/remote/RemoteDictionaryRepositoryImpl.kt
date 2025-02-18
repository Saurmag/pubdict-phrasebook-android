package com.example.data_repository.repository.remote

import com.example.data_repository.datasource.remote.RemoteDictionaryDataSource
import com.example.domain.entity.dictionary.Word
import com.example.domain.repository.remote.RemoteDictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDictionaryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDictionaryDataSource
): RemoteDictionaryRepository {
    override fun getWordList(
        srcLangIso: String,
        tarLangIso: String,
        query: String,
        limit: Int,
        offset: Int
    ): Flow<List<Word>> = remoteDataSource.getWordList(srcLangIso, tarLangIso, query, limit, offset)

    override fun getWord(srcLangIso: String, tarLangIso: String, word: String): Flow<Word> =
        remoteDataSource.getWord(srcLangIso, tarLangIso, word)
}