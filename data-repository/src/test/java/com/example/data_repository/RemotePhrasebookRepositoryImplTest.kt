package com.example.data_repository

import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.data_repository.repository.RemotePhrasebookRepositoryImpl
import com.example.domain.entity.phrasebook.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class RemotePhrasebookRepositoryImplTest {
    private val remotePhrasebookDataSource = mock(RemotePhrasebookDataSource::class.java)
    private val remotePhrasebookRepositoryImpl = RemotePhrasebookRepositoryImpl(
        remotePhrasebookDataSource = remotePhrasebookDataSource)
    private val phrasebook = Phrasebook(
        id = 1,
        title = "lez",
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus")
    )
    private val phrasebookList = listOf(
        Phrasebook(
            id = 1,
            title = "lez",
            originLanguage = Language(id = 1, title = "lez", iso = "lez"),
            translatedLanguage = Language(id = 2, title = "rus", iso = "rus")
        ),
        Phrasebook(
            id = 2,
            title = "lez",
            originLanguage = Language(id = 1, title = "lez", iso = "lez"),
            translatedLanguage = Language(id = 2, title = "tur", iso = "tur")
        ),
        Phrasebook(
            id = 1,
            title = "lez",
            originLanguage = Language(id = 1, title = "lez", iso = "lez"),
            translatedLanguage = Language(id = 2, title = "eng", iso = "eng")
        )
    )
    private val topicList = listOf(
        Topic(
            id = 1,
            originTitle = "000",
            countPhrase = 1,
            originLanguage = Language(id = 1, title = "lez", iso = "lez"),
            translatedLanguage = Language(id = 2, title = "rus", iso = "rus"),
            phraseList = listOf(
                Phrase(
                    id = 1,
                    topicId = 1,
                    translatedText = "000",
                    originText = "000",
                    ipaTextTransliteration = "000",
                    enTextTransliteration = "000"
                )
            ),
        )
    )
    private val topic = Topic(
        id = 1,
        originTitle = "000",
        countPhrase = 1,
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus"),
        phraseList = listOf(
            Phrase(
            id = 1,
            topicId = 1,
            translatedText = "000",
            originText = "000",
            ipaTextTransliteration = "000",
            enTextTransliteration = "000"
        )
        ),
    )
    private val phrase = Phrase(
        id = 1,
        topicId = 1,
        translatedText = "000",
        originText = "000",
        ipaTextTransliteration = "000",
        enTextTransliteration = "000"
    )

    @Test
    fun getPhrasebookTest() = runTest {
        whenever(remotePhrasebookRepositoryImpl.getPhrasebook(1)).thenReturn(flowOf(phrasebook))
        val result = remotePhrasebookRepositoryImpl.getPhrasebook(1).first()
        assertEquals(result, phrasebook)
    }

    @Test
    fun getPhrasebookListTest() = runTest {
        whenever(remotePhrasebookRepositoryImpl.getPhrasebookList()).thenReturn(flowOf(phrasebookList))
        val result = remotePhrasebookRepositoryImpl.getPhrasebookList().first()
        assertEquals(result, phrasebookList)
    }

    @Test
    fun getTopicTest() = runTest {
        whenever(remotePhrasebookRepositoryImpl.getTopic(1)).thenReturn(flowOf(topic))
        val result = remotePhrasebookRepositoryImpl.getTopic(1).first()
        assertEquals(result, topic)
    }

    @Test
    fun getTopicListTest() = runTest {
        whenever(remotePhrasebookRepositoryImpl.getTopicList()).thenReturn(flowOf(topicList))
        val result = remotePhrasebookRepositoryImpl.getTopicList().first()
        assertEquals(result, topicList)
    }

    @Test
    fun getPhraseTest() = runTest {
        whenever(remotePhrasebookRepositoryImpl.getPhrase(1)).thenReturn(flowOf(phrase))
        val result = remotePhrasebookRepositoryImpl.getPhrase(1).first()
        assertEquals(result, phrase)
    }
}