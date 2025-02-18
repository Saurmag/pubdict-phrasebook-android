package com.example.data_remote.injection

import com.example.data_remote.network.PublicDictionaryService
import com.example.data_remote.network.model.phrase.NetworkPhraseJsonAdapterClass
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    @Product
    @Provides
    fun provideProductBaseUrl(): String = "https://api.publicdictionary.org/"

    @Staging
    @Provides
    fun provideStagingBaseUrl(): String = "https://staging-api.publicdictionary.org/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(NetworkPhraseJsonAdapterClass())
        .build()

    @Singleton
    @Provides
    fun provideRetrofitProduct(
        @Product baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun providePublicDictionaryService(
        retrofit: Retrofit
    ): PublicDictionaryService = retrofit.create(PublicDictionaryService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Staging

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Product