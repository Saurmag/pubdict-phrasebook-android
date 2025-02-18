package com.example.data_remote.source

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_remote.network.model.category.Category
import com.example.data_remote.network.model.category.CategoryReduced
import com.example.data_remote.network.model.category.NetworkLanguage
import com.example.data_remote.network.model.phrase.NetworkPhrase
import com.example.data_remote.network.model.phrasebook.NetworkPhrasebook
import com.example.data_repository.datasource.remote.RemotePhrasebookDataSource
import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import com.example.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemotePhrasebookDataSourceImpl @Inject constructor(
    private val service: PublicDictionaryService
) : RemotePhrasebookDataSource {

    override fun getPhrasebook(id: Int, tarLangIso: String): Flow<Phrasebook> = flow {
        val phrasebook = service.fetchNetworkPhrasebook(id)
            .mapToPhrasebook(tarLangIso)
        emit(phrasebook)
    }.catch {
        throw UseCaseException.PhrasebookException(it)
    }

    override fun getTopicList(srcLangIso: String, tarLangIso: String): Flow<List<Topic>> = flow {
        val filteredCategoryList = service.fetchCategoryList(
            srcLangIso = srcLangIso,
            tarLangIso = tarLangIso
        ).categoryList
            .filter { category ->
                category.categoryTranslationList.isNotEmpty() && category.categoryTranslationList
                    .any { categoryTranslation ->
                        categoryTranslation.networkLanguage.iso == tarLangIso
                    }
        }
        val topicList = filteredCategoryList
            .map { category ->
            category.mapToTopic(
                srcLangIso = srcLangIso,
                tarLangIso = tarLangIso
            )
        }
        emit(topicList)
    }.catch {
        throw UseCaseException.TopicException(it)
    }

    override fun getTranslateLanguageList(srcLangIso: String): Flow<List<Language>> = flow {
        val tranLangList = service
            .fetchTranslateNetworkLanguage(srcLangIso = srcLangIso).networkTranslateLanguageList
            .map { it.mapToLanguage() }
        emit(tranLangList)
    }.catch {
        throw UseCaseException.LanguageException(it)
    }

    override fun getTopic(
        id: Int,
        srcLangIso: String,
        tarLangIso: String
    ): Flow<Topic> = flow {
        val categoryReduced = service.fetchCategoryReduced(id)
        val topic = categoryReduced.let {
            val networkPhraseList = service.fetchPhraseList(
                categoryId = it.id,
                srcLangIso = srcLangIso,
                tarLangIso = tarLangIso
            ).networkPhraseList
                .filter { networkPhrase ->
                    networkPhrase.translation.isNotBlank() && networkPhrase.translationOrigin.isNotBlank()
                }
            it.mapToTopic(
                networkPhraseList = networkPhraseList,
                srcLangIso = srcLangIso,
                tarLangIso = tarLangIso
            )
        }
        emit(topic)
    }.catch {
        throw UseCaseException.TopicException(it)
    }

    override fun getPhrase(id: Int): Flow<Phrase> = flow {
        val phrase = service.fetchPhrase(id).mapToPhrase()
        emit(phrase)
    }.catch {
        throw UseCaseException.PhraseException(cause = it)
    }
}

private fun NetworkLanguage.mapToLanguage() =
    Language(
        id = this.id,
        title = this.title,
        iso = this.iso
    )

private fun NetworkPhrasebook.mapToPhrasebook(tarLangIso: String) =
    Phrasebook(
        id = this.id,
        title = this.title,
        originLanguage = this.originNetworkLanguage.mapToLanguage(),
        translatedLanguage = this.translatedNetworkLanguageList.first { it.iso == tarLangIso }.mapToLanguage()
    )

private fun Category.mapToTopic(
    srcLangIso: String,
    tarLangIso: String
) = Topic(
    id = this.id,
    originTitle = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == srcLangIso
    }.name,
    translatedTitle = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == tarLangIso
    }.name,
    countPhrase = this.countPhrases,
    originLanguage = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == srcLangIso
    }.networkLanguage.mapToLanguage(),
    translatedLanguage = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == tarLangIso
    }.networkLanguage.mapToLanguage(),
    phraseList = listOf()
)

private fun CategoryReduced.mapToTopic(
    networkPhraseList: List<NetworkPhrase>,
    srcLangIso: String,
    tarLangIso: String
) = Topic(
    id = this.id,
    originTitle = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == srcLangIso
    }.name,
    translatedTitle = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == tarLangIso
    }.name,
    countPhrase = networkPhraseList.size,
    originLanguage = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == srcLangIso
    }.networkLanguage.mapToLanguage(),
    translatedLanguage = this.categoryTranslationList.first { categoryTranslation ->
        categoryTranslation.networkLanguage.iso == tarLangIso
    }.networkLanguage.mapToLanguage(),
    phraseList = networkPhraseList.map { it.mapToPhrase() }
)

private fun NetworkPhrase.mapToPhrase() =
    Phrase(
        id = this.id,
        topicId = this.categoryId,
        originText = this.translationOrigin,
        translatedText = this.translation,
        ipaTextTransliteration = this.phoneticIpa ?: "",
        enTextTransliteration = this.phoneticEn ?: ""
    )