package com.example.domain

import com.example.domain.entity.phrasebook.Language
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.repository.RemotePhrasebookRepository
import com.example.domain.usecase.GetPhrasebookUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPhrasebookUseCaseTest {
    private val remoteRepository = mock(RemotePhrasebookRepository::class.java)
    private val phrasebook = Phrasebook(
        id = 1,
        title = "lez",
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus")
    )

    @Test
    fun getPhrasebookFromRemoteTest() = runTest {
        val useCase = GetPhrasebookUseCase(
            remoteRepository = remoteRepository,
            mock()
        )
        val request = GetPhrasebookUseCase.Request(phrasebookId = 1, tarLangIso = "rus")
        whenever(remoteRepository.getPhrasebook(request.phrasebookId, request.tarLangIso)).thenReturn(flowOf(phrasebook))
        val response = useCase.process(request).first()
        assertEquals(response, GetPhrasebookUseCase.Response(phrasebook))
    }
}