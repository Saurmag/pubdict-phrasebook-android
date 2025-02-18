package com.example.data_repository.datasource.remote

import com.example.domain.entity.dictionary.WordOfDay
import kotlinx.coroutines.flow.Flow

interface RemoteWordOfDayDataSource {
    fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>>
}
