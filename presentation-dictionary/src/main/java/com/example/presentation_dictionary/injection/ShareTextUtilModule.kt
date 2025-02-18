package com.example.presentation_dictionary.injection

import com.example.domain.usecase.share.ShareTextFormatter
import com.example.domain.usecase.share.ShareTextUtil
import com.example.presentation_dictionary.phrase.PhraseModel
import com.example.presentation_dictionary.word.single.DetailWordModel
import com.example.presentation_dictionary.dictionary.SharePhraseFormatter
import com.example.presentation_dictionary.dictionary.ShareTextUtilImpl
import com.example.presentation_dictionary.dictionary.ShareWordFormatter
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Module
abstract class ShareTextUtilModule {

    @PhraseFormatter
    @DictionaryScope
    @Binds
    abstract fun bindSharePhraseFormatter(
        sharePhraseFormatter: SharePhraseFormatter
    ): ShareTextFormatter<@JvmWildcard PhraseModel>

    @DetailWordFormatter
    @DictionaryScope
    @Binds
    abstract fun bindShareWordFormatter(
        shareWordFormatter: ShareWordFormatter
    ): ShareTextFormatter<@JvmWildcard DetailWordModel>

    @DictionaryScope
    @Binds
    abstract fun bindShareTextUtil(
        shareTextUtilImpl: ShareTextUtilImpl
    ): ShareTextUtil
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PhraseFormatter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DetailWordFormatter