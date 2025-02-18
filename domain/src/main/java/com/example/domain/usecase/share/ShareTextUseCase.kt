package com.example.domain.usecase.share

import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ShareTextUseCase<T>(
    private val shareTextFormatter: ShareTextFormatter<T>,
    private val shareTextUtil: ShareTextUtil,
    configuration: Configuration
): UseCase<ShareTextUseCase.Request<T>, ShareTextUseCase.Response>(configuration) {

    override fun process(request: Request<T>): Flow<Response> {
        val text = shareTextFormatter.format(request.shareText)
        shareTextUtil.shareText(text)
        return flowOf(Response)
    }

    data class Request<T>(val shareText: T) : UseCase.Request
    object Response : UseCase.Response
}