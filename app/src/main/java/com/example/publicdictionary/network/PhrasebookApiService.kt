package com.example.publicdictionary.network

import com.example.publicdictionary.network.model.category.PhrasebookCategories
import com.example.publicdictionary.network.model.phrase.PhrasesList
import retrofit2.http.GET
import retrofit2.http.Query

interface PhrasebookApiService {
    @GET("phrasebooks/categories/?")
    suspend fun fetchPhrasebook(
        @Query(value = "source_lang_iso") phrasebookLanguage: String,
        @Query(value = "target_lang_iso") systemLanguage: String
    ): PhrasebookCategories

    @GET("phrases/")
    suspend fun fetchPhrasesList(
        @Query(value = "limit") limit: Int,
        @Query(value = "offset") offset: Int,
        @Query(value = "source_lang_iso") phrasebookLanguage: String,
        @Query(value = "target_lang_iso") systemLanguage: String
    ): PhrasesList

    @GET("phrases/")
    suspend fun fetchPhrasesListCategory(
        @Query(value = "category") categoryId: Int,
        @Query(value = "limit") limit: Int,
        @Query(value = "source_lang_iso") phrasebookLanguage: String,
        @Query(value = "target_lang_iso") systemLanguage: String
    ): PhrasesList
}