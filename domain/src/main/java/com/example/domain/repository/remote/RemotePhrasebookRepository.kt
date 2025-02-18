package com.example.domain.repository.remote

import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import kotlinx.coroutines.flow.Flow

interface RemotePhrasebookRepository {
    fun getPhrasebook(id: Int, tarLangIso: String): Flow<Phrasebook>

    fun getTopicList(srcLangIso: String, tarLangIso: String): Flow<List<Topic>>

    fun getTranslateLanguageList(srcLangIso: String): Flow<List<Language>>

    fun getTopic(id: Int, srcLangIso: String, tarLangIso: String): Flow<Topic>

    fun getPhrase(id: Int): Flow<Phrase>
}