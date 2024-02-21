package com.example.publicdictionary.network

import com.example.publicdictionary.network.model.category.PhrasebookCategories
import com.example.publicdictionary.network.model.phrase.PhrasesList
import retrofit2.http.GET

interface PhrasebookApiService {
    @GET("http://staging-api.publicdictionary.org/phrasebooks/categories/?limit=100&source_lang_iso=lez&target_lang_iso=eng")
    suspend fun fetchPhrasebook(): PhrasebookCategories

    @GET("http://staging-api.publicdictionary.org/phrases/?limit=100&offset=1&source_lang_iso=lez&target_lang_iso=eng")
    suspend fun fetchPhrasesList(): PhrasesList
}