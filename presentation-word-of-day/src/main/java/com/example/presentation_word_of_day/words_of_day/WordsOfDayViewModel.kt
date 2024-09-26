package com.example.presentation_word_of_day.words_of_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetWordOfDayListUseCase
import com.example.presentation_common.ORIGIN_LANGUAGE_ISO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class WordsOfDayViewModel(
    private val useCase: GetWordOfDayListUseCase,
    private val converter: WordsOfDayConverter
): ViewModel() {

    private val _wordsOfDayFlow: MutableStateFlow<WordsOfDayUiState> = MutableStateFlow(
        WordsOfDayUiState(isLoading = true)
    )

    val wordsOfDayFlow: StateFlow<WordsOfDayUiState>
        get() = _wordsOfDayFlow.asStateFlow()

    fun loadWordsOfDay() {
        viewModelScope.launch {
            useCase.execute(request = GetWordOfDayListUseCase.Request(srcLangIso = ORIGIN_LANGUAGE_ISO))
                .map { converter.convert(result = it) }
                .collect { _wordsOfDayFlow.value = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val useCase: Provider<GetWordOfDayListUseCase>,
        private val converter: Provider<WordsOfDayConverter>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == WordsOfDayViewModel::class.java)
            return WordsOfDayViewModel(useCase.get(), converter.get()) as T
        }
    }
}