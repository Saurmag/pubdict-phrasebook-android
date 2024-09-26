package com.example.presentation_phrasebook.phrase.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetPhraseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PhraseViewModel(
    private val phraseUseCase: GetPhraseUseCase,
    private val converter: PhraseConverter
): ViewModel() {

    private var _phraseFlow: MutableStateFlow<PhraseUiState> = MutableStateFlow(PhraseUiState(isLoading = true))

    val phraseFlow: StateFlow<PhraseUiState>
        get() = _phraseFlow.asStateFlow()

    fun loadPhrase(phraseId: Int) {
        viewModelScope.launch {
            phraseUseCase.execute(
                request = GetPhraseUseCase.Request(
                    phraseId = phraseId
                )
            ).map {
                converter.convert(it)
            }.collect {
                _phraseFlow.value = it
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val phraseUseCase: Provider<GetPhraseUseCase>,
        private val converter: Provider<PhraseConverter>
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == PhraseViewModel::class.java)
            return PhraseViewModel(phraseUseCase.get(), converter.get()) as T
        }
    }
}