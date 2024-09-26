package com.example.data_repository.repository

import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.RemotePhrasebookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePhrasebookRepositoryImpl @Inject constructor(
    private val remotePhrasebookDataSource: RemotePhrasebookDataSource
): RemotePhrasebookRepository {
    override fun getPhrasebook(id: Int, tarLangIso: String): Flow<Phrasebook> =
        remotePhrasebookDataSource.getPhrasebook(id, tarLangIso)

    override fun getTopicList(srcLangIso: String, tarLangIso: String): Flow<List<Topic>> =
        remotePhrasebookDataSource.getTopicList(srcLangIso, tarLangIso)

    override fun getTopic(id: Int, srcLangIso: String, tarLangIso: String): Flow<Topic> =
        remotePhrasebookDataSource.getTopic(id, srcLangIso, tarLangIso)

    override fun getPhrase(id: Int): Flow<Phrase> =
        remotePhrasebookDataSource.getPhrase(id)
}