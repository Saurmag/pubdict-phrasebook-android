package com.example.data_local

import com.example.data_local.db.PhrasebookDao
import com.example.data_local.db.entity.PhraseEntity
import com.example.data_local.db.entity.PhrasebookEntity
import com.example.data_local.db.entity.TopicEntity
import com.example.data_local.db.entity.TranslatedLanguageEntity
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.entity.phrasebook.Language
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalPhrasebookDataSourceImplTest {

    private val phrasebookDao = mock(PhrasebookDao::class.java)
    private val localDataSourceConfig = LocalDataSourceConfig()
    private val localDataSource = LocalPhrasebookDataSourceImpl(
        phrasebookDao = phrasebookDao,
        localScope = localDataSourceConfig.scope,
        io = localDataSourceConfig.dispatcher
    )

    @Test
    fun getPhraseTest() = runTest {
        val phraseEntity = PhraseEntity(
            id = 1,
            topicId = 1,
            text = "0",
            textTranslation = "0",
            ipaTextTransliteration = "0",
            enTextTransliteration = "0"
        )
        val expected = Phrase(
            id = 1,
            topicId = 1,
            translatedText = "0",
            originText = "0",
            ipaTextTransliteration = "0",
            enTextTransliteration = "0"
        )

        whenever(phrasebookDao.getPhrase(phraseId = phraseEntity.id)).thenReturn(flowOf(phraseEntity))
        val result = localDataSource.getPhrase(phraseEntity.id).first()
        assertEquals(expected, result)
    }

    @Test
    fun getTopicTest() = runTest {
        val topicEntity = TopicEntity(
            id = 1,
            title = "0",
            countPhrases = 1,
            phrasebookId = 1
        )
        val phraseEntities = listOf(PhraseEntity(
            id = 1,
            topicId = 1,
            text = "0",
            textTranslation = "0",
            ipaTextTransliteration = "0",
            enTextTransliteration = "0"
        ))
        val expected = Topic(
            id = 1,
            originTitle = "0",
            countPhrase = 1,
            phraseList = listOf(
                Phrase(
                id = 1,
                topicId = 1,
                translatedText = "0",
                originText = "0",
                ipaTextTransliteration = "0",
                enTextTransliteration = "0"
            )
            )
        )

        whenever(phrasebookDao.getTopic(topicId = topicEntity.id)).thenReturn(flowOf(mapOf(topicEntity to phraseEntities)))
        val result = localDataSource.getTopic(topicEntity.id).first()
        assertEquals(expected, result)
    }

    @Test
    fun getPhrasebookTest() = runTest {
        val originLanguageEntity = OriginLanguageEntity(
            id = 1,
            title = "0",
            iso = "0"
        )
        val translatedLanguageEntity = TranslatedLanguageEntity(
            id = 1,
            title = "0",
            iso = "0"
        )
        val phrasebookEntity = PhrasebookEntity(
            id = 1,
            title = "0",
            originLanguage = originLanguageEntity,
            translatedLanguage = translatedLanguageEntity
        )
        val topicEntity = TopicEntity(
            id = 1,
            title = "0",
            countPhrases = 1,
            phrasebookId = 1
        )
        val topicEntities = listOf(TopicEntity(
            id = 1,
            title = "0",
            countPhrases = 1,
            phrasebookId = 1
        ))
        val phraseEntities = listOf(PhraseEntity(
            id = 1,
            topicId = 1,
            text = "0",
            textTranslation = "0",
            ipaTextTransliteration = "0",
            enTextTransliteration = "0"
        ))
        val expected = Phrasebook(
            id = 1,
            title = "0",
            originLanguage = OriginLanguage(1, "0", "0"),
            translatedLanguage = Language(1, "0", "0"),
            topics = listOf(
                Topic(
                id = 1,
                originTitle = "0",
                countPhrase = 1,
                phraseList = listOf(
                    Phrase(
                    id = 1,
                    topicId = 1,
                    translatedText = "0",
                    originText = "0",
                    ipaTextTransliteration = "0",
                    enTextTransliteration = "0"
                )
                )
            )
            )
        )

        whenever(phrasebookDao.getPhrasebookAndTopics(phrasebookId = phrasebookEntity.id)).thenReturn(
            flowOf(mapOf(phrasebookEntity to topicEntities))
        )
        whenever(phrasebookDao.getTopic(topicId = topicEntities.first().id)).thenReturn(
            flowOf(mapOf(topicEntity to phraseEntities))
        )
        val result = localDataSource.getPhrasebook(phrasebookEntity.id).first()
        assertEquals(expected, result)
    }

    @Test
    fun insertPhrasebookTest() = runTest {
        val originLanguageEntity = OriginLanguageEntity(
            id = 1,
            title = "0",
            iso = "0"
        )
        val translatedLanguageEntity = TranslatedLanguageEntity(
            id = 1,
            title = "0",
            iso = "0"
        )
        val phrasebookEntity = PhrasebookEntity(
            id = 1,
            title = "0",
            originLanguage = originLanguageEntity,
            translatedLanguage = translatedLanguageEntity
        )
        val topicEntities = listOf(TopicEntity(
            id = 1,
            title = "0",
            countPhrases = 1,
            phrasebookId = 1
        ))
        val phraseEntities = listOf(PhraseEntity(
            id = 1,
            topicId = 1,
            text = "0",
            textTranslation = "0",
            ipaTextTransliteration = "0",
            enTextTransliteration = "0"
        ))
        val phrasebook = Phrasebook(
            id = 1,
            title = "0",
            originLanguage = OriginLanguage(1, "0", "0"),
            translatedLanguage = Language(1, "0", "0"),
            topics = listOf(
                Topic(
                id = 1,
                originTitle = "0",
                countPhrase = 1,
                phraseList = listOf(
                    Phrase(
                    id = 1,
                    topicId = 1,
                    translatedText = "0",
                    originText = "0",
                    ipaTextTransliteration = "0",
                    enTextTransliteration = "0"
                )
                )
            )
            )
        )

        localDataSource.insertPhrasebook(phrasebook = phrasebook)
        verify(phrasebookDao).insertPhrasebook(phrasebookEntity = phrasebookEntity)
        verify(phrasebookDao).insertTopics(topicEntities = topicEntities)
        verify(phrasebookDao).insertPhrases(phraseEntities = phraseEntities)
    }
}