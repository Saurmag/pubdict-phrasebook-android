package com.example.publicdictionary.data

import com.example.publicdictionary.network.PhrasebookApiService
import com.example.publicdictionary.network.model.category.PhrasebookCategories
import com.example.publicdictionary.network.model.phrase.PhrasesList
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.model.Phrasebook
import com.example.publicdictionary.ui.model.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale

interface PhrasebookRepository {
    suspend fun getPhrasebook(): Flow<Phrasebook>
}

class PhrasebookRepositoryImpl(
    private val phrasebookApiService: PhrasebookApiService
) : PhrasebookRepository {
    override suspend fun getPhrasebook(): Flow<Phrasebook> = flow {
        val systemLanguage = Locale.getDefault().isO3Language
        val targetLanguage = "lez"
        val phrasebookCategories = phrasebookApiService.fetchPhrasebook(
            phrasebookLanguage = targetLanguage,
            systemLanguage = systemLanguage
        )
        val phrasesList = phrasebookApiService.fetchPhrasesList(
            limit = 2000,
            offset = 1,
            phrasebookLanguage = targetLanguage,
            systemLanguage = systemLanguage
        )
        val phrasebook = convert(
            phrasebookCategories, phrasesList, systemLanguage
        )
        emit(phrasebook)
    }
}

private fun convert(
    phrasebookCategories: PhrasebookCategories,
    phrasesList: PhrasesList,
    systemLanguage: String,
): Phrasebook {
    val sortedCategoryList = phrasebookCategories.categories.filter {  category ->
        category.categoryTranslations.isNotEmpty() && category.categoryTranslations.any {
            it.language.iso == systemLanguage
        }
    }
    val sortedPhrasesList = phrasesList.phrasesList.asSequence().filter { networkPhrase ->
        networkPhrase.translations.size == 2
    }
    return Phrasebook(
        topics = sortedCategoryList.map { category ->
            Topic(
                id = category.id,
                title = category.categoryTranslations.last().name,
                countPhrases = category.countPhrases,
                phrases = sortedPhrasesList
                    .filter { phrase -> phrase.idCategory == category.id }
                    .toList()
                    .map { phrase ->
                    val textInSystemLanguage = phrase.translations.last().translation
                    val textInDestinationLanguage = phrase.translations.first().translation
                    val ipaText = phrase.translations.first().ipaPhonetic ?: ""
                    val enText = phrase.translations.first().enPhonetic ?: ""
                    Phrase(
                        id = phrase.id,
                        topicId = phrase.idCategory,
                        text = textInSystemLanguage,
                        textTranslation = textInDestinationLanguage,
                        ipaTextTransliteration = ipaText,
                        enTextTransliteration = enText
                    )
                }
            )
        }.filter { topic -> topic.phrases.isNotEmpty() }
    )
}