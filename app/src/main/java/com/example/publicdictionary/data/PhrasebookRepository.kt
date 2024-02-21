package com.example.publicdictionary.data

import com.example.publicdictionary.ui.model.Phrasebook
import com.example.publicdictionary.network.PhrasebookApiService
import com.example.publicdictionary.network.model.category.PhrasebookCategories
import com.example.publicdictionary.network.model.phrase.PhrasesList
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.model.Topic

interface PhrasebookRepository {
    suspend fun getPhrasebook(): Phrasebook
}

class PhrasebookRepositoryImpl(
    private val phrasebookApiService: PhrasebookApiService
) : PhrasebookRepository {
    override suspend fun getPhrasebook(): Phrasebook {
        val phrasebookCategories = phrasebookApiService.fetchPhrasebook()
        val phrasesList = phrasebookApiService.fetchPhrasesList()
        return convert(
            phrasebookCategories = phrasebookCategories,
            phrasesList = phrasesList
        )
    }
}

private fun convert(
    phrasebookCategories: PhrasebookCategories,
    phrasesList: PhrasesList
): Phrasebook {
    val sortedCategoryList = phrasebookCategories.categories.filter {  category ->
        category.categoryTranslations.isNotEmpty() && category.categoryTranslations.any {
            it.language.iso == "eng"
        }
    }
    val sortedPhrasesList = phrasesList.phrasesList.filter { networkPhrase ->
        networkPhrase.translations.isNotEmpty() && networkPhrase.translations.size >= 2
    }
    return Phrasebook(
        topics = sortedCategoryList.map { category ->
            Topic(
                title = category.categoryTranslations.last().name,
                countPhrases = category.countPhrases,
                phrases = sortedPhrasesList.map { phrase ->
                    val textInSystemLanguage = phrase.translations.last().translation
                    val textInDestinationLanguage = phrase.translations.first().translation
                    val ipaText = phrase.translations.first().ipaPhonetic ?: ""
                    val enText = phrase.translations.first().enPhonetic ?: ""
                    Phrase(
                        text = textInSystemLanguage,
                        textTranslation = textInDestinationLanguage,
                        ipaTextTransliteration = ipaText,
                        enTextTransliteration = enText
                    )
                }
            )
        }
    )
}