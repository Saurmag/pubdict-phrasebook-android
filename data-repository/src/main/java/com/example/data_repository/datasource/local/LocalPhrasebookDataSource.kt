package com.example.data_repository.datasource.local

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import kotlinx.coroutines.flow.Flow

interface LocalPhrasebookDataSource {

    fun getPhrasebook(id: Int): Flow<Phrasebook>

    fun getTopic(id: Int): Flow<Topic>

    fun getPhrase(id: Int): Flow<Phrase>

    fun getTopicList(): Flow<List<Topic>>

    suspend fun insertPhrasebookList(phrasebookList: List<Phrasebook>)

    suspend fun insertTopicList(topicList: List<Topic>)
}