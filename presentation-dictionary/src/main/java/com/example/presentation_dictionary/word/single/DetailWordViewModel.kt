package com.example.presentation_dictionary.word.single

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.dictionary.GetWordUseCase
import com.example.domain.usecase.language.GetTranslationLanguageUseCase
import com.example.domain.usecase.share.ShareTextUseCase
import com.example.presentation_dictionary.BuildConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Provider

class DetailWordViewModel(
    private val wordUseCase: GetWordUseCase,
    private val tranLangUseCase: GetTranslationLanguageUseCase,
    private val shareWordUseCase: ShareTextUseCase<DetailWordModel>,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<WordUiState, WordSideEffect> {

    override val container: Container<WordUiState, WordSideEffect> =
        container(initialState = WordUiState(isLoading = true)) {
            val word = savedStateHandle.get<String>("word")
            word?.let { getDetailWord(it) }
        }

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            throwable.message?.let {
                reduce {
                    state.copy(isLoading = false, exception = throwable)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getDetailWord(word: String) = intent {
        viewModelScope.launch(context = ceh) {
            tranLangUseCase.execute(request = GetTranslationLanguageUseCase.Request)
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
                        reduce { state.copy(isLoading = false, word = it.word.mapToDetailWordModel()) }
                    }
                    wordResult.onFailure {
                        reduce { state.copy(isLoading = false, exception = it) }
                    }
                }
        }
    }

    fun handleEvent(event: WordEvent) {
        when(event) {
            is WordEvent.BackToDictionary -> backToDictionary()
            is WordEvent.DismissDialog -> dismissDialog()
            is WordEvent.ShareWord -> shareWord(event.wordModel)
            is WordEvent.ShowDialog -> showDialog()
        }
    }

    private fun dismissDialog() = intent {
        reduce { state.copy(isLoading = false, dialogState = false) }
    }

    private fun showDialog() = intent {
        reduce { state.copy(isLoading = false, dialogState = true) }
    }

    private fun backToDictionary() = intent {
        postSideEffect(sideEffect = WordSideEffect.NavigateToDictionary())
    }

    private fun shareWord(word: DetailWordModel) = intent {
        shareWordUseCase.execute(request = ShareTextUseCase.Request(shareText = word))
    }

    class Factory @AssistedInject constructor(
        private val wordUseCase: Provider<GetWordUseCase>,
        private val tranLangUseCase: Provider<GetTranslationLanguageUseCase>,
        private val shareWordUseCase: Provider<ShareTextUseCase<DetailWordModel>>,
        @Assisted("saved_state_handle") private val savedStateHandle: SavedStateHandle,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DetailWordViewModel::class.java)
            return DetailWordViewModel(
                wordUseCase = wordUseCase.get(),
                tranLangUseCase = tranLangUseCase.get(),
                shareWordUseCase = shareWordUseCase.get(),
                savedStateHandle = savedStateHandle
            ) as T
        }
    }
}

@AssistedFactory
interface DetailWordViewModelAssistedFactory {
    fun create(@Assisted("saved_state_handle") savedStateHandle: SavedStateHandle): DetailWordViewModel.Factory
}

@Immutable
data class WordUiState(
    val word: DetailWordModel? = null,
    val isLoading: Boolean = false,
    val dialogState: Boolean = false,
    val exception: Throwable? = null,
)

sealed class WordSideEffect {
    class NavigateToDictionary : WordSideEffect()
}

sealed class WordEvent {
    data class ShareWord(val wordModel: DetailWordModel) : WordEvent()
    class ShowDialog : WordEvent()
    class BackToDictionary : WordEvent()
    class DismissDialog : WordEvent()
}