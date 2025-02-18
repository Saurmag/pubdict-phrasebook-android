package com.example.data_remote

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_remote.network.model.category.Category
import com.example.data_remote.network.model.category.CategoryList
import com.example.data_remote.network.model.category.CategoryReduced
import com.example.data_remote.network.model.category.CategoryTranslation
import com.example.data_remote.network.model.category.NetworkLanguage
import com.example.data_remote.network.model.phrase.NetworkPhrase
import com.example.data_remote.network.model.phrase.NetworkPhraseList
import com.example.data_remote.network.model.phrasebook.NetworkPhrasebook
import com.example.data_remote.source.RemotePhrasebookDataSourceImpl
import com.example.domain.entity.dictionary.Language
import com.example.domain.entity.phrasebook.Phrase
import com.example.domain.entity.phrasebook.Phrasebook
import com.example.domain.entity.phrasebook.Topic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class RemotePhrasebookDataSourceImplTest {
    private val publicDictionaryService = mock(PublicDictionaryService::class.java)
    private val remoteDataSource = RemotePhrasebookDataSourceImpl(publicDictionaryService)
    private val networkPhrasebook = NetworkPhrasebook(
        id = 1,
        title = "lez",
        isPublished = true,
        originNetworkLanguage = NetworkLanguage(id = 1, title = "lez", iso = "lez"),
        translatedNetworkLanguageList = listOf(
            NetworkLanguage(id = 2, title = "rus", iso = "rus"),
            NetworkLanguage(id = 3, title = "tur", iso = "tur")
        )
    )
    private val phrasebook = Phrasebook(
        id = 1,
        title = "lez",
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus")
    )
    private val categoryReduced = CategoryReduced(
        id = 1,
        categoryTranslationList = listOf(
            CategoryTranslation(
                id = 1,
                name = "burzum",
                networkLanguage = NetworkLanguage(id = 1, "lez", iso = "lez")
            ),
            CategoryTranslation(
                id = 2,
                name = "бурзум",
                networkLanguage = NetworkLanguage(id = 2, "rus", iso = "rus")
            )
        )
    )
    private val category = Category(
        id = 1,
        countPhrases = 1,
        categoryTranslationList = listOf(
            CategoryTranslation(
                id = 1,
                name = "burzum",
                networkLanguage = NetworkLanguage(id = 1, "lez", iso = "lez")
            ),
            CategoryTranslation(
                id = 2,
                name = "бурзум",
                networkLanguage = NetworkLanguage(id = 2, "rus", iso = "rus")
            )
        )
    )
    private val networkPhrase = NetworkPhrase(
        id = 1,
        categoryId = 1,
        translationOrigin = "burzum",
        translation = "бурзум",
        phoneticEn = "111111",
        phoneticIpa = "111111"
    )
    private val phrase = Phrase(
        id = 1,
        topicId = 1,
        translatedText = "бурзум",
        originText = "burzum",
        ipaTextTransliteration = "111111",
        enTextTransliteration = "111111"
    )
    private val topic = Topic(
        id = 1,
        originTitle = "burzum",
        translatedTitle = "бурзум",
        countPhrase = 1,
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus"),
        phraseList = listOf(
            Phrase(
            id = 1,
            topicId = 1,
            translatedText = "бурзум",
            originText = "burzum",
            ipaTextTransliteration = "111111",
            enTextTransliteration = "111111"
        )
        ),
    )
    private val topicListObject = Topic(
        id = 1,
        originTitle = "burzum",
        translatedTitle = "бурзум",
        countPhrase = 1,
        originLanguage = Language(id = 1, title = "lez", iso = "lez"),
        translatedLanguage = Language(id = 2, title = "rus", iso = "rus"),
        phraseList = listOf()
    )

        @Test
    fun getPhrasebookTest() = runTest {
        whenever(publicDictionaryService.fetchNetworkPhrasebook(1)).thenReturn(networkPhrasebook)
        val result = remoteDataSource.getPhrasebook(1, "rus").first()
        assertEquals(result, phrasebook)
    }

    @Test
    fun getTopicListTest() = runTest {
        whenever(
            publicDictionaryService
                .fetchCategoryList(srcLangIso = "lez", tarLangIso = "rus")
        ).thenReturn(CategoryList(listOf(category)))
        val result = remoteDataSource.getTopicList("lez", "rus").first()
        assertEquals(result, listOf(topicListObject))
    }

    @Test
    fun getTopicTest() = runTest {
        whenever(
            publicDictionaryService
                .fetchCategoryReduced(1)
        ).thenReturn(categoryReduced)
        whenever(
            publicDictionaryService
                .fetchPhraseList(categoryId = 1, srcLangIso = "lez", tarLangIso = "rus")
        ).thenReturn(NetworkPhraseList(listOf(networkPhrase)))
        val result = remoteDataSource.getTopic(1,"lez", "rus").first()
        assertEquals(result, topic)
    }

    @Test
    fun getPhraseTest() = runTest {
        whenever(publicDictionaryService.fetchPhrase(1)).thenReturn(networkPhrase)
        val result = remoteDataSource.getPhrase(1).first()
        assertEquals(result, phrase)
    }
}