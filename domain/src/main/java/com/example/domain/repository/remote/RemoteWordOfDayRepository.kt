package com.example.domain.repository.remote

import com.example.domain.entity.dictionary.WordOfDay
import kotlinx.coroutines.flow.Flow

interface RemoteWordOfDayRepository {

    fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>>
}