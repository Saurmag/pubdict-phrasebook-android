package com.example.domain

import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.phrasebook.GetTopicListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetTopicListUseCaseTest {
    private val remoteRepository = mock(RemotePhrasebookRepository::class.java)
    private val topicList = listOf(
        Topic(
        id = 1,
        originTitle = "000",
        translatedTitle = "000",
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

    @Test
    fun getPhrasebookFromRemoteAndTopicListFromRemoteTest() = runTest {
        val useCase = GetTopicListUseCase(
            remoteRepository = remoteRepository,
            mock()
        )
        val request = GetTopicListUseCase.Request("lez", "rus")
        whenever(remoteRepository.getTopicList(request.srcLangIso, request.tarLangIso)).thenReturn(flowOf(topicList))
        val response = useCase.process(request).first()
        assertEquals(response, GetTopicListUseCase.Response(topicList = topicList))
    }
}