package com.example.publicdictionary.data

import com.example.publicdictionary.network.PhrasebookApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val phrasebookRepository: PhrasebookRepository
}

class DefaultAppContainer : AppContainer{

    private val baseUrl = "http://staging-api.publicdictionary.org/"



    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val phrasebookService by lazy {
        retrofit.create(PhrasebookApiService::class.java)
    }

    override val phrasebookRepository: PhrasebookRepository by lazy {
        PhrasebookRepositoryImpl(phrasebookApiService = phrasebookService)
    }
}