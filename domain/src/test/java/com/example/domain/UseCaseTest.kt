package com.example.domain

import com.example.domain.usecase.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock


class UseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val configuration = UseCase.Configuration(
        mock(),
        UnconfinedTestDispatcher()
    )
    private val request = mock<UseCase.Request>()
    private val response = mock<UseCase.Response>()

    private var _useCase: UseCase<UseCase.Request, UseCase.Response>? = null
    private val useCase: UseCase<UseCase.Request, UseCase.Response>
        get() = checkNotNull(_useCase)

    @Before
    fun setUp() {
        _useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, request)
                return flowOf(response)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testExecuteSuccess() = runTest(UnconfinedTestDispatcher()) {
        val result = useCase.execute(request).first()
        assertEquals(Result.success(response), result)
    }
}