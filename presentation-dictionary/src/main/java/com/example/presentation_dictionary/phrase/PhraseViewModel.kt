package com.example.presentation_dictionary.phrase

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.phrasebook.GetPhraseUseCase
import com.example.domain.usecase.share.ShareTextUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Provider

class PhraseViewModel(
    private val phraseUseCase: GetPhraseUseCase,
    private val shareTextUseCase: ShareTextUseCase<PhraseModel>,
    savedStateHandle: SavedStateHandle
): ViewModel(), ContainerHost<PhraseUiState, PhraseSideEffect> {

    override val container: Container<PhraseUiState, PhraseSideEffect> =
        container(
            initialState = PhraseUiState(isLoading = true),
        ) {
            val phraseId = savedStateHandle.get<Int>(key = "phraseId")
            phraseId?.let { getPhrase(it) }
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

    fun handleEvent(event: PhraseEvent) {
        when(event) {
            is PhraseEvent.SharePhrase -> shareText(event.phraseModel)
            is PhraseEvent.BackToTopic -> backToTopic()
            is PhraseEvent.ShowDialog -> showDialog()
            is PhraseEvent.DismissDialog -> dismissDialog()
        }
    }

    private fun getPhrase(phraseId: Int) = intent {
        viewModelScope.launch(ceh) {
            phraseUseCase.execute(
                request = GetPhraseUseCase.Request(
                    phraseId = phraseId
                )
            ).collect { result ->
                result.onSuccess { response ->
                    reduce {
                        val phraseModel = response.phrase.mapToPhraseModel()
                        state.copy(isLoading = false, phraseModel = phraseModel)
                    }
                }
                result.onFailure { exception ->
                    reduce {
                        state.copy(isLoading = false, exception = exception)
                    }
                }
            }
        }
    }

    private fun dismissDialog() = intent {
        reduce { state.copy(isLoading = false, dialogState = false) }
    }

    private fun showDialog() = intent {
        reduce { state.copy(isLoading = false, dialogState = true) }
    }

    private fun backToTopic() = intent {
        postSideEffect(sideEffect = PhraseSideEffect.NavigateToTopic())
    }

    private fun shareText(phrase: PhraseModel) = intent {
        shareTextUseCase.execute(request = ShareTextUseCase.Request(phrase))
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @AssistedInject constructor(
        private val phraseUseCase: Provider<GetPhraseUseCase>,
        private val shareTextUseCase: Provider<ShareTextUseCase<PhraseModel>>,
        @Assisted("saved_state_handle") private val savedStateHandle: SavedStateHandle
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == PhraseViewModel::class.java)
            return PhraseViewModel(phraseUseCase.get(), shareTextUseCase.get(), savedStateHandle) as T
        }
    }
}

@AssistedFactory
interface PhraseViewModelAssistedFactory {
    fun create(@Assisted("saved_state_handle") savedStateHandle: SavedStateHandle): PhraseViewModel.Factory
}

@Immutable
data class PhraseUiState(
    val isLoading: Boolean = false,
    val dialogState: Boolean = false,
    val exception: Throwable? = null,
    val phraseModel: PhraseModel? = null
)

sealed class PhraseSideEffect {
    class NavigateToTopic : PhraseSideEffect()
}

sealed class PhraseEvent {
    data class SharePhrase(val phraseModel: PhraseModel): PhraseEvent()
    class ShowDialog : PhraseEvent()
    class BackToTopic : PhraseEvent()
    class DismissDialog : PhraseEvent()
}