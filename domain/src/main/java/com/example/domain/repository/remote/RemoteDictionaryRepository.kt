package com.example.domain.repository.remote

import com.example.domain.entity.dictionary.Word
import kotlinx.coroutines.flow.Flow

interface RemoteDictionaryRepository {

    fun getWordList(
        srcLangIso: String,
        tarLangIso: String,
        query: String = "",
        limit: Int,
        offset: Int
    ): Flow<List<Word>>

    fun getWord(srcLangIso: String, tarLangIso: String, word: String): Flow<Word>
}