package com.example.presentation_dictionary.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.domain.repository.local.LocalTranslationLanguageRepository
import com.example.domain.repository.remote.RemoteDictionaryRepository
import com.example.domain.repository.remote.RemotePhrasebookRepository
import com.example.domain.repository.remote.RemoteWordOfDayRepository
import javax.inject.Qualifier
import kotlin.properties.Delegates.notNull

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

interface RepositoryDeps {
    val wordOfDayRepository: RemoteWordOfDayRepository
    val dictionaryRepository: RemoteDictionaryRepository
    val phrasebookRepository: RemotePhrasebookRepository
    val tranLangRepository: LocalTranslationLanguageRepository
}

interface ContextDeps {
    val applicationContext: Context
}

interface ActivityDeps {
    val activityContext: Context
}

interface DictionaryDepsProvider {
    var repositoryDeps: RepositoryDeps
    var contextDeps: ContextDeps
    var activityDeps: ActivityDeps
    companion object : DictionaryDepsProvider by DictionaryDepsStore
}

object DictionaryDepsStore : DictionaryDepsProvider {
    override var repositoryDeps: RepositoryDeps by notNull()
    override var contextDeps: ContextDeps by notNull()
    override var activityDeps: ActivityDeps by notNull()
}

internal class DictionaryComponentViewModel : ViewModel() {
    val dictionaryComponent =
        DaggerDictionaryComponent.builder()
            .depsRepositoryDeps(deps = DictionaryDepsProvider.repositoryDeps)
            .applicationContext(context = DictionaryDepsProvider.contextDeps.applicationContext)
            .activityContext(context = DictionaryDepsProvider.activityDeps.activityContext)
            .build()
}