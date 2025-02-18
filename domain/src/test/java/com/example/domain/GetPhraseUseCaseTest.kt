package com.example.domain

import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.usecase.phrasebook.GetPhraseUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPhraseUseCaseTest {
    private val remoteRepository = mock(RemotePhrasebookRepository::class.java)
    private val phrase = Phrase(
        id = 1,
        topicId = 1,
        translatedText = "000",
        originText = "000",
        ipaTextTransliteration = "000",
        enTextTransliteration = "000"
    )

    @Test
    fun getTopicFromRemoteTest() = runTest {
        val useCase = GetPhraseUseCase(
            remoteRepository = remoteRepository,
            mock()
        )
        val request = GetPhraseUseCase.Request(phraseId = 1)
        whenever(remoteRepository.getPhrase(request.phraseId)).thenReturn(flowOf(phrase))
        val response = useCase.process(request).first()
        assertEquals(response, GetPhraseUseCase.Response(phrase))
    }
}