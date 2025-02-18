package com.example.data_repository.repository.remote

import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.remote.RemotePhrasebookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePhrasebookRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemotePhrasebookDataSource
): RemotePhrasebookRepository {
    override fun getPhrasebook(id: Int, tarLangIso: String): Flow<Phrasebook> =
        remoteDataSource.getPhrasebook(id, tarLangIso)

    override fun getTopicList(srcLangIso: String, tarLangIso: String): Flow<List<Topic>> =
        remoteDataSource.getTopicList(srcLangIso, tarLangIso)

    override fun getTranslateLanguageList(srcLangIso: String): Flow<List<Language>> =
        remoteDataSource.getTranslateLanguageList(srcLangIso)

    override fun getTopic(id: Int, srcLangIso: String, tarLangIso: String): Flow<Topic> =
        remoteDataSource.getTopic(id, srcLangIso, tarLangIso)

    override fun getPhrase(id: Int): Flow<Phrase> =
        remoteDataSource.getPhrase(id)
}