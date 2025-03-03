package com.example.presentation_dictionary.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.entity.dictionary.Language
import com.example.domain.usecase.dictionary.GetWordOfDayListUseCase
import com.example.domain.usecase.dictionary.GetWordUseCase
import com.example.domain.usecase.language.GetTranslateLanguageListUseCase
import com.example.domain.usecase.language.GetTranslationLanguageUseCase
import com.example.domain.usecase.language.UpdateTranslationLanguageUseCase
import com.example.domain.usecase.phrasebook.GetTopicListUseCase
import com.example.domain.usecase.share.ShareTextUseCase
import com.example.presentation_dictionary.BuildConfig
import com.example.presentation_dictionary.phrasebook.PhrasebookUiState
import com.example.presentation_dictionary.phrasebook.mapToTopicListItemModel
import com.example.presentation_dictionary.word.list.WordModel
import com.example.presentation_dictionary.word.list.WordPagingSourceFactory
import com.example.presentation_dictionary.word.single.DetailWordModel
import com.example.presentation_dictionary.word.single.mapToDetailWordModel
import com.example.presentation_dictionary.words_of_day.WordOfDayUiState
import com.example.presentation_dictionary.words_of_day.mapToWordOfDayModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import javax.inject.Provider

@OptIn(OrbitExperimental::class)
class DictionaryViewModel(
    private val updateTranslationLanguageUseCase: UpdateTranslationLanguageUseCase,
    private val tranLanguagesUseCase: GetTranslateLanguageListUseCase,
    private val phrasebookUseCase: GetTopicListUseCase,
    private val tranLanguageUseCase: GetTranslationLanguageUseCase,
    private val wordsOfDayUseCase: GetWordOfDayListUseCase,
    private val wordPagingSourceFactory: WordPagingSourceFactory,
    private val shareWordUseCase: ShareTextUseCase<DetailWordModel>,
    private val wordUseCase: GetWordUseCase,
) : ViewModel(), ContainerHost<DictionaryUiState, DictionarySideEffect> {

    override val container: Container<DictionaryUiState, DictionarySideEffect> =
        container(
            initialState = DictionaryUiState(
                wordsOfDay = WordOfDayUiState(isLoading = true),
                phrasebook = PhrasebookUiState(isLoading = true),
                tranLanguage = TranslationLanguageUiState(isSelected = true),
                tranLanguages = TranslationLanguagesUiState(isLoading = true)
            ),
        ) {
            viewModelScope.launch {
                launch { getTranslationLanguages() }
                launch { getTranslationLanguage() }
                launch { getWordOfDay() }
                launch { getPhrasebook() }
                launch { getWordsFlow() }
            }
        }

    fun handleEvent(event: DictionaryEvent) {
        when(event) {
            is DictionaryEvent.CleanSearchQuery -> cleanSearchQueryIntent()
            is DictionaryEvent.EnterSearchQuery -> enterSearchQuery(event.inputData)
            is DictionaryEvent.SelectTopic -> selectTopic(event.topicId)
            is DictionaryEvent.SelectWord -> selectWord(event.word)
            is DictionaryEvent.ChangeTranslationLanguage -> changeTranslationLanguage(event.language)
            is DictionaryEvent.ShareWord -> shareWordOfDay(event.word)
        }
    }

    private fun changeTranslationLanguage(language: LanguageModel) = intent {
        viewModelScope.launch {
            val languageParam = Language(id = language.id, title = language.title, iso = language.iso)
            val request = UpdateTranslationLanguageUseCase.Request(languageParam)
            updateTranslationLanguageUseCase.execute(request)
            getWordsFlow()
            getPhrasebook()
        }
    }

    private fun cleanSearchQueryIntent() = intent {
        viewModelScope.launch {
            reduce { state.copy(searchQuery = "") }
        }
        viewModelScope.launch {
            getWordsFlow()
        }
    }

    private fun enterSearchQuery(query: String) = blockingIntent {
        viewModelScope.launch {
            reduce { state.copy(searchQuery = query) }
        }
        viewModelScope.launch {
            getWordsFlow()
        }
    }

    private fun selectTopic(topicId: Int) = intent {
        postSideEffect(sideEffect = DictionarySideEffect.NavigateToTopic(topicId))
    }

    private fun selectWord(word: String) = intent {
        postSideEffect(sideEffect = DictionarySideEffect.NavigateToWord(word))
    }

    private suspend fun getWordsFlow() = subIntent {
        val langRequest = GetTranslationLanguageUseCase.Request
        tranLanguageUseCase.execute(langRequest)
            .collectLatest { langResult ->
                langResult.onSuccess { langResponse ->
                    val wordPagingSource = wordPagingSourceFactory.create(
                        tarLangIso = langResponse.translationLanguage.iso,
                        query = state.searchQuery
                    )
                    val pagerFlow = Pager(config = PagingConfig(pageSize = 50)) { wordPagingSource }.flow.cachedIn(viewModelScope)
                    reduce { state.copy(wordsPageFlow = pagerFlow) }
                }
            }
    }

    private suspend fun getTranslationLanguage() = subIntent {
        val request = GetTranslationLanguageUseCase.Request
        tranLanguageUseCase.execute(request)
            .collect { languagesResult ->
                languagesResult.onSuccess {
                    val language = it.translationLanguage.mapToLanguageModel()
                    reduce { state.copy(tranLanguage = state.tranLanguage.copy(isSelected = true, language = language)) }
                }
            }
    }

    private suspend fun getTranslationLanguages() = subIntent {
        val request = GetTranslateLanguageListUseCase.Request(BuildConfig.originLanguageIso)
        tranLanguagesUseCase.execute(request)
            .collect { languagesResult ->
                languagesResult.onSuccess { response ->
                    val languages = response.translateLanguageList.map { language -> language.mapToLanguageModel() }
                    reduce { state.copy(tranLanguages = state.tranLanguages.copy(isLoading = false, languages = languages)) }
                }
            }
    }

    private suspend fun getPhrasebook() = subIntent {
        val langRequest = GetTranslationLanguageUseCase.Request
        tranLanguageUseCase.execute(langRequest)
            .collectLatest { langResult ->
                langResult.onSuccess { langResponse ->
                    val request = GetTopicListUseCase.Request(
                        srcLangIso = BuildConfig.originLanguageIso,
                        tarLangIso = langResponse.translationLanguage.iso
                    )
                    phrasebookUseCase.execute(request)
                        .collect { phrasebookResult ->
                            phrasebookResult.onSuccess {
                                val phrasebook = it.topicList.map { topic -> topic.mapToTopicListItemModel() }
                                reduce { state.copy(phrasebook = state.phrasebook.copy(isLoading = false, topicList = phrasebook)) }
                            }
                            phrasebookResult.onFailure {
                                reduce { state.copy(phrasebook = state.phrasebook.copy(isLoading = false, exception = it)) }
                            }
                        }
                }
                langResult.onFailure {
                    reduce { state.copy(phrasebook = state.phrasebook.copy(isLoading = false, exception = it)) }
                }
            }
    }

    private suspend fun getWordOfDay() = subIntent {
        val request = GetWordOfDayListUseCase.Request(BuildConfig.originLanguageIso)
        wordsOfDayUseCase.execute(request)
            .collect { wordOfDayResult ->
                wordOfDayResult.onSuccess {
                    val wordsOfDay = it.wordOfDayList.map { wordOfDay -> wordOfDay.mapToWordOfDayModel() }
                    reduce { state.copy(wordsOfDay = state.wordsOfDay.copy(isLoading = false, wordsOfDay = wordsOfDay)) }
                }
                wordOfDayResult.onFailure {
                    reduce { state.copy(wordsOfDay = state.wordsOfDay.copy(isLoading = false, exception = it)) }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun shareWordOfDay(word: String) = intent {
        viewModelScope.launch {
            tranLanguageUseCase.execute(request = GetTranslationLanguageUseCase.Request)
                .map { it.getOrThrow().translationLanguage }
                .flatMapLatest { language ->
                    val request = GetWordUseCase.Request(
                        srcLangIso = BuildConfig.originLanguageIso,
                        tarLangIso = language.iso,
                        word = word
                    )
                    wordUseCase.execute(request)
                }
                .collect { wordResult ->
                    wordResult.onSuccess {
                        shareWordUseCase.execute(request = ShareTextUseCase.Request(shareText = it.word.mapToDetailWordModel()))
                    }
                    wordResult.onFailure { exception ->
                        exception.localizedMessage?.let {
                            postSideEffect(sideEffect = DictionarySideEffect.ShowToast(it))
                        }
                    }
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val updateTranslationLanguageUseCase: Provider<UpdateTranslationLanguageUseCase>,
        private val tranLanguagesUseCase: Provider<GetTranslateLanguageListUseCase>,
        private val phrasebookUseCase: Provider<GetTopicListUseCase>,
        private val tranLanguageUseCase: Provider<GetTranslationLanguageUseCase>,
        private val wordsOfDayUseCase: Provider<GetWordOfDayListUseCase>,
        private val wordPagingSourceFactory: Provider<WordPagingSourceFactory>,
        private val shareTextUseCase: Provider<ShareTextUseCase<DetailWordModel>>,
        private val getWordUseCase: Provider<GetWordUseCase>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DictionaryViewModel::class.java)
            return DictionaryViewModel(
                updateTranslationLanguageUseCase = updateTranslationLanguageUseCase.get(),
                tranLanguagesUseCase = tranLanguagesUseCase.get(),
                phrasebookUseCase = phrasebookUseCase.get(),
                tranLanguageUseCase = tranLanguageUseCase.get(),
                wordsOfDayUseCase = wordsOfDayUseCase.get(),
                wordPagingSourceFactory = wordPagingSourceFactory.get(),
                shareWordUseCase = shareTextUseCase.get(),
                wordUseCase = getWordUseCase.get()
            ) as T
        }
    }
}

data class DictionaryUiState(
    val wordsOfDay: WordOfDayUiState = WordOfDayUiState(),
    val phrasebook: PhrasebookUiState = PhrasebookUiState(),
    val tranLanguage: TranslationLanguageUiState = TranslationLanguageUiState(),
    val tranLanguages: TranslationLanguagesUiState = TranslationLanguagesUiState(),
    val searchQuery: String = "",
    val wordsPageFlow: Flow<PagingData<WordModel>> = emptyFlow()
)

sealed class DictionaryEvent {
    data class SelectWord(val word: String): DictionaryEvent()
    data class SelectTopic(val topicId: Int): DictionaryEvent()
    data class EnterSearchQuery(val inputData: String): DictionaryEvent()
    class CleanSearchQuery : DictionaryEvent()
    data class ChangeTranslationLanguage(val language: LanguageModel): DictionaryEvent()
    data class ShareWord(val word: String): DictionaryEvent()
}

sealed class DictionarySideEffect {
    data class NavigateToWord(val word: String): DictionarySideEffect()
    data class NavigateToTopic(val topicId: Int): DictionarySideEffect()
    data class ShowToast(val message: String): DictionarySideEffect()
}