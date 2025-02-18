package com.example.presentation_dictionary.injection

import android.content.Context
import com.example.presentation_dictionary.dictionary.DictionaryNavigation
import com.example.presentation_dictionary.phrase.PhraseNavigation
import com.example.presentation_dictionary.topic.TopicNavigation
import com.example.presentation_dictionary.word.single.WordNavigation
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@DictionaryScope
@Component(
    modules = [
        TopicModule::class,
        PhraseModule::class,
        PhrasebookModule::class,
        DictionaryModule::class,
        WordsModule::class,
        ShareTextUtilModule::class
    ],
    dependencies = [RepositoryDeps::class]
)
internal interface DictionaryComponent {

    fun inject(topicNavigation: TopicNavigation)

    fun inject(dictionaryNavigation: DictionaryNavigation)

    fun inject(phraseNavigation: PhraseNavigation)

    fun inject(wordNavigation: WordNavigation)

    @Component.Builder
    interface Builder {
        fun depsRepositoryDeps(deps: RepositoryDeps): Builder

        @BindsInstance
        fun applicationContext(context: Context): Builder

        @BindsInstance
        fun activityContext(@ActivityContext context: Context): Builder

        fun build(): DictionaryComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DictionaryScope
