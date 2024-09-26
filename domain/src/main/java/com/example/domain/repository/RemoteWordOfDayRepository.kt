package com.example.domain.repository

import com.example.domain.entity.WordOfDay
import kotlinx.coroutines.flow.Flow

interface RemoteWordOfDayRepository {

    fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>>
}