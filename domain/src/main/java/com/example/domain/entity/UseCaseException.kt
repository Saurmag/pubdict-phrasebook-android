package com.example.domain.entity

sealed class UseCaseException(cause: Throwable): Throwable(cause) {

    class PhrasebookException(cause: Throwable): UseCaseException(cause)

    class TopicException(cause: Throwable): UseCaseException(cause)

    class PhraseException(cause: Throwable): UseCaseException(cause)

    class WordOfDayException(cause: Throwable): UseCaseException(cause)

    class UnknownException(cause: Throwable): UseCaseException(cause)

    companion object {
        fun createFromThrowable(throwable: Throwable): UseCaseException {
            return if (throwable is UseCaseException)
                throwable
                   else UnknownException(throwable)
        }
    }
}