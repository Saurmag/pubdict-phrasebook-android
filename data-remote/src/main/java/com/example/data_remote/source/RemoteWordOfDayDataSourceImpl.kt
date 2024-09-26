package com.example.data_remote.source

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_remote.network.model.wordofday.NetworkImageWordOfDay
import com.example.data_remote.network.model.wordofday.NetworkWordOfDay
import com.example.data_repository.datasource.remote.RemoteWordOfDayDataSource
import com.example.domain.entity.ImageWordOfDay
import com.example.domain.entity.UseCaseException
import com.example.domain.entity.WordOfDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteWordOfDayDataSourceImpl @Inject constructor(
    private val publicDictionaryService: PublicDictionaryService
): RemoteWordOfDayDataSource {
    override fun getWordOfDayList(srcLangIso: String): Flow<List<WordOfDay>> = flow {
        val wordOfDayList = publicDictionaryService.fetchWordOfDayList(srcLangIso = srcLangIso)
            .map { it.mapToWordOfDay() }
        emit(wordOfDayList)
    }.catch {
        throw UseCaseException.WordOfDayException(cause = it)
    }
}

private fun NetworkWordOfDay.mapToWordOfDay() =
    WordOfDay(
        id = this.id,
        entryId = this.entryId ?: 0,
        word = this.word,
        languageIso = this.languageIso,
        ipaWordTransliteration = this.ipaWordTransliteration ?: "",
        enWordTransliteration = this.enWordTransliteration ?: "",
        image = this.networkImage?.mapToImageWordOfDay() ?: ImageWordOfDay("", 0, 0, 0)
    )

private fun NetworkImageWordOfDay.mapToImageWordOfDay() =
    ImageWordOfDay(
        url = this.url,
        width = this.width,
        height = this.height,
        size = this.size
    )