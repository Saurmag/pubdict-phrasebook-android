package com.example.domain.repository

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import kotlinx.coroutines.flow.Flow

interface LocalPhrasebookRepository {
    fun getPhrasebook(id: Int): Flow<Phrasebook>

    fun getTopicList(): Flow<List<Topic>>

    fun getTopic(id: Int): Flow<Topic>

    fun getPhrase(id: Int): Flow<Phrase>

    fun insertPhrasebookList(phrasebookList: List<Phrasebook>)

    fun insertTopicList(topicList: List<Topic>)
}