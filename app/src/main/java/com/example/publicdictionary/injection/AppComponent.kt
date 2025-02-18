package com.example.publicdictionary.injection

import android.content.Context
import com.example.data_local.injection.DataLocalComponent
import com.example.data_remote.injection.DataRemoteComponent
import com.example.data_repository.injection.DataRepositoryComponent
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import com.example.presentation_dictionary.injection.ActivityContext
import com.example.presentation_dictionary.injection.ActivityDeps
import com.example.presentation_dictionary.injection.ApplicationContext
import com.example.presentation_dictionary.injection.ContextDeps
import com.example.presentation_dictionary.injection.RepositoryDeps
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ApplicationScope

@ApplicationScope
@Component(
    dependencies = [DataRepositoryComponent::class, DataRemoteComponent::class, DataLocalComponent::class, HelperComponent::class]
)
interface AppComponent : RepositoryDeps, ContextDeps, ActivityDeps {

    override val wordOfDayRepository: RemoteWordOfDayRepository

    override val dictionaryRepository: RemoteDictionaryRepository

    override val phrasebookRepository: RemotePhrasebookRepository

    override val tranLangRepository: LocalTranslationLanguageRepository

    @get:ActivityContext
    override val activityContext: Context

    @get:ApplicationContext
    override val applicationContext: Context

    @Component.Builder
    interface Builder {

        fun depsHelperComponent(helperComponent: HelperComponent): Builder

        fun depsDataLocalComponent(dataLocalComponent: DataLocalComponent): Builder

        fun depsDataRepositoryComponent(dataRepositoryComponent: DataRepositoryComponent): Builder

        fun depsDataRemoteComponent(dataRemoteComponent: DataRemoteComponent): Builder

        fun build(): AppComponent
    }
}