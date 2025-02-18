package com.example.presentation_dictionary.word.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.usecase.dictionary.GetWordListUseCase
import com.example.presentation_dictionary.BuildConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PUBLIC_DICTIONARY_INITIAL_OFFSET = 2
private const val PUBLIC_DICTIONARY_LIMIT = 100

class WordPagingSource @AssistedInject constructor(
    private val wordsUseCase: GetWordListUseCase,
    @Assisted("tarLangIso") private val tarLangIso: String,
    @Assisted("query") private val query: String
) : PagingSource<Int, WordModel>() {
    override fun getRefreshKey(state: PagingState<Int, WordModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(100) ?: anchorPage?.nextKey?.minus(100)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WordModel> {
        val nextOffsetNumber = params.key ?: PUBLIC_DICTIONARY_INITIAL_OFFSET
        val request = GetWordListUseCase.Request(
            srcLangIso = BuildConfig.originLanguageIso,
            tarLangIso = tarLangIso,
            query = query,
            limit = PUBLIC_DICTIONARY_LIMIT,
            offset = nextOffsetNumber
        )
        val words =
            wordsUseCase.execute(request = request)
                .map {
                    val result = it.getOrNull()
                    result?.wordList?.map { word -> WordModel(originText = word.text) }
                }
                .lastOrNull()

        if (words == null) {
            return LoadResult.Error(IOException("Something Wrong"))
        }
        val nextOffset =
            if (nextOffsetNumber == 25000 || query.isNotBlank() && words.size < 20) null else nextOffsetNumber.plus(
                100
            )
        return LoadResult.Page(
            data = words,
            nextKey = nextOffset,
            prevKey = null
        )
    }
}

@AssistedFactory
interface WordPagingSourceFactory {
    fun create(
        @Assisted("tarLangIso") tarLangIso: String,
        @Assisted("query") query: String
    ): WordPagingSource
}