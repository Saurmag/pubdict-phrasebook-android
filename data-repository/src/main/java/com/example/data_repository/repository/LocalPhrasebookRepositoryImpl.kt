package com.example.data_repository.repository

import com.example.data_repository.datasource.local.LocalPhrasebookDataSource
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.LocalPhrasebookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LocalPhrasebookRepositoryImpl(
    private val localPhrasebookDataSource: LocalPhrasebookDataSource,
    private val configuration: RepositoryConfiguration
) : LocalPhrasebookRepository {
    override fun getPhrasebook(id: Int): Flow<Phrasebook> =
        localPhrasebookDataSource.getPhrasebook(id)

    override fun getTopicList(): Flow<List<Topic>> =
        localPhrasebookDataSource.getTopicList()

    override fun getTopic(id: Int): Flow<Topic> =
        localPhrasebookDataSource.getTopic(id)

    override fun getPhrase(id: Int): Flow<Phrase> =
        localPhrasebookDataSource.getPhrase(id)

    override fun insertPhrasebookList(phrasebookList: List<Phrasebook>) {
        configuration.scope.launch(context = configuration.dispatcher) {
            localPhrasebookDataSource.insertPhrasebookList(phrasebookList)
        }
    }

    override fun insertTopicList(topicList: List<Topic>) {
        configuration.scope.launch(context = configuration.dispatcher) {
            localPhrasebookDataSource.insertTopicList(topicList)
        }
    }
}