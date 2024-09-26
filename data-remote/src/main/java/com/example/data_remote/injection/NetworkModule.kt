package com.example.data_remote.injection

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_remote.network.model.phrase.NetworkPhraseJsonAdapterClass
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Scope
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NetworkPhraseJsonAdapterClass())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://staging-api.publicdictionary.org/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun providePublicDictionaryService(
        retrofit: Retrofit
    ): PublicDictionaryService = retrofit.create(PublicDictionaryService::class.java)
}