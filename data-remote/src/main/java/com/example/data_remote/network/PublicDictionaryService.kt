package com.example.data_remote.network

import com.example.data_remote.network.model.category.CategoryList
import com.example.data_remote.network.model.category.CategoryReduced
import com.example.data_remote.network.model.phrase.NetworkPhrase
import com.example.data_remote.network.model.phrase.NetworkPhraseList
import com.example.data_remote.network.model.phrasebook.NetworkPhrasebook
import com.example.data_remote.network.model.phrasebook.NetworkTranslateLanguageList
import com.example.data_remote.network.model.word.NetworkWordList
import com.example.data_remote.network.model.word.NetworkWordTranslation
import com.example.data_remote.network.model.wordofday.NetworkWordOfDay
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DEVICE = "ANDROID"

interface PublicDictionaryService {

    @GET("phrasebooks/{id}")
    suspend fun fetchNetworkPhrasebook(id: Int): NetworkPhrasebook

    @GET("phrasebooks/categories/")
    suspend fun fetchCategoryList(
        @Query(value = "source_lang_iso") srcLangIso: String,
        @Query(value = "target_lang_iso") tarLangIso: String
    ): CategoryList

    @GET("phrases/")
    suspend fun fetchPhraseList(
        @Query(value = "category") categoryId: Int,
        @Query(value = "source_lang_iso") srcLangIso: String,
        @Query(value = "target_lang_iso") tarLangIso: String
    ): NetworkPhraseList

    @GET("phrases/{id}")
    suspend fun fetchPhrase(@Path("id") id: Int): NetworkPhrase

    @GET("phrasebooks/categories/{id}")
    suspend fun fetchCategoryReduced(@Path("id") id: Int): CategoryReduced

    @GET("v2/words_of_day/")
    suspend fun fetchWordOfDayList(@Query(value = "source_lang") srcLangIso: String): List<NetworkWordOfDay>

    @GET("language/{iso}")
    suspend fun fetchTranslateNetworkLanguage(@Path("iso") srcLangIso: String): NetworkTranslateLanguageList

    @GET("v2/entries/translate/")
    suspend fun fetchNetworkEntryWordFullTranslation(
        @Query(value = "device") device: String = DEVICE,
        @Query(value = "source_lang") srcLangIso: String,
        @Query(value = "target_lang") tarLangIso: String,
        @Query(value = "word") word: String
    ): List<NetworkWordTranslation>

    @GET("words/")
    suspend fun fetchNetworkWordList(
        @Query(value = "source_lang") srcLangIso: String,
        @Query(value = "target_lang") tarLangIso: String,
        @Query(value = "q") query: String,
        @Query(value = "limit") limit: Int = 100,
        @Query(value = "offset") offset: Int = 1
    ): NetworkWordList
}