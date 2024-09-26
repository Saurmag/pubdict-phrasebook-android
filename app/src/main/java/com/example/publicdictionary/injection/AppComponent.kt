package com.example.publicdictionary.injection

import com.example.data_local.injection.DataLocalComponent
import com.example.data_remote.injection.DataRemoteComponent
import com.example.data_repository.injection.DataRepositoryComponent
import com.example.presentation_phrasebook.injection.PhrasebookViewModelModule
import com.example.presentation_word_of_day.words_of_day.WordsOfDayViewModelModule
import com.example.publicdictionary.MainActivity
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ApplicationScope

@ApplicationScope
@Component(
    modules = [PhrasebookViewModelModule::class, WordsOfDayViewModelModule::class],
    dependencies = [DataRepositoryComponent::class, DataRemoteComponent::class, DataLocalComponent::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        fun depsDataLocalComponent(dataLocalComponent: DataLocalComponent): Builder

        fun depsDataRepositoryComponent(dataRepositoryComponent: DataRepositoryComponent): Builder

        fun depsDataRemoteComponent(dataRemoteComponent: DataRemoteComponent): Builder

        fun build(): AppComponent
    }
}