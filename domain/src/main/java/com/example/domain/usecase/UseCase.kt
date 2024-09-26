package com.example.domain.usecase

import com.example.domain.entity.UseCaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class UseCase<I : UseCase.Request, O : UseCase.Response>(
    private val configuration: Configuration
) {

    fun execute(request: I) = process(request)
        .map {
            Result.success(it) as Result<O>
        }
        .flowOn(configuration.dispatcher)
        .catch {
            emit(Result.failure(UseCaseException.createFromThrowable(it)))
        }

    internal abstract fun process(request: I): Flow<O>

    interface Request
    interface Response

    class Configuration(val scope: CoroutineScope, val dispatcher: CoroutineDispatcher)
}