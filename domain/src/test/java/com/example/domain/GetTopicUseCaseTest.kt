package com.example.domain


import com.example.domain.entity.phrasebook.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.repository.RemotePhrasebookRepository
import com.example.domain.usecase.GetTopicUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetTopicUseCaseTest {
    private val remoteRepository = mock(RemotePhrasebookRepository::class.java)
    private val topic = Topic(
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

    @Test
    fun getTopicFromRemoteTest() = runTest {
        val useCase = GetTopicUseCase(
            remoteRepository = remoteRepository,
            mock()
        )
        val request = GetTopicUseCase.Request(topicId = 1, srcLangIso = "lez", tarLangIso = "rus")
        whenever(remoteRepository.getTopic(request.topicId, request.srcLangIso, request.tarLangIso)).thenReturn(flowOf(topic))
        val response = useCase.process(request).first()
        assertEquals(response, GetTopicUseCase.Response(topic))
    }
}