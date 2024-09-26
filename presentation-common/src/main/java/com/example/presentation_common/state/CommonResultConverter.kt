package com.example.presentation_common.state

abstract class CommonResultConverter<T : Any, R : UiState> {

    fun convert(result: Result<T>): R {
        return when(result.isSuccess) {
            true -> {
                 convertSuccess(data = result.getOrThrow())
            }
            false -> {
                convertError(exception = result.exceptionOrNull())
            }
        }
    }

    protected abstract fun convertSuccess(data: T): R
    protected abstract fun convertError(exception: Throwable?): R
}