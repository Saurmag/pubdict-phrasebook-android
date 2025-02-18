package com.example.data_remote.source

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_repository.datasource.remote.RemoteDictionaryDataSource
import com.example.domain.entity.UseCaseException
import com.example.domain.entity.dictionary.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDictionaryDataSourceImpl @Inject constructor(
    private val service: PublicDictionaryService
) : RemoteDictionaryDataSource {
    override fun getWordList(
        srcLangIso: String,
        tarLangIso: String,
        query: String,
        limit: Int,
        offset: Int
    ): Flow<List<Word>> = flow {
        val wordList =
            service.fetchNetworkWordList(
                srcLangIso = srcLangIso,
                tarLangIso = tarLangIso,
                query = query,
                limit = limit,
                offset = offset
            ).networkWordList
                .asSequence()
                .distinctBy { it.word }
                .sortedBy { it.word.first().lowercaseChar() }
                .map { networkWord ->
                    Word(
                        id = 0,
                        text = networkWord.word,
                        translate = "",
                        ipaTransliteration = "",
                        enTransliteration = ""
                    )
                }
                .toList()
        emit(wordList)
    }
        .catch {
            throw UseCaseException.WordException(it)
        }

    override fun getWord(srcLangIso: String, tarLangIso: String, word: String): Flow<Word> = flow {
        val wordFullTranslation = service.fetchNetworkEntryWordFullTranslation(
            word = word,
            srcLangIso = srcLangIso,
            tarLangIso = tarLangIso
        ).first()
        val detailWord = Word(
            id = wordFullTranslation.id,
            text = wordFullTranslation.word,
            translate = wordFullTranslation.translation,
            ipaTransliteration = wordFullTranslation.ipaTransliteration,
            enTransliteration = wordFullTranslation.enTransliteration
        )
        emit(detailWord)
    }.catch {
        throw UseCaseException.WordException(it)
    }
}