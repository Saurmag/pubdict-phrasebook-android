package com.example.presentation_dictionary.topic

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.language.GetTranslationLanguageUseCase
import com.example.domain.usecase.phrasebook.GetTopicUseCase
import com.example.presentation_dictionary.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import javax.inject.Provider

class TopicViewModel(
    private val topicUseCase: GetTopicUseCase,
    private val tranLangUseCase: GetTranslationLanguageUseCase,
) : ViewModel(), ContainerHost<TopicUiState, TopicSideEffect> {

    override val container: Container<TopicUiState, TopicSideEffect> =
        container(initialState = TopicUiState(isLoading = true))

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        intent {
            throwable.message?.let {
                reduce {
                    state.copy(isLoading = false, exception = throwable)
                }
            }
        }
    }

    fun handleEvent(event: TopicEvent) {
        when (event) {
            is TopicEvent.SelectPhrase -> selectPhrase(event.phraseId)
            is TopicEvent.BackToDictionary -> backToDictionary()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTopic(topicId: Int) = intent {
        viewModelScope.launch(ceh) {
            tranLangUseCase.execute(request = GetTranslationLanguageUseCase.Request)
                .map { it.getOrThrow().translationLanguage }
                .flatMapLatest { language ->
                    val request = GetTopicUseCase.Request(
                        srcLangIso = BuildConfig.originLanguageIso,
                        tarLangIso = language.iso,
                        topicId = topicId
                    )
                    topicUseCase.execute(request = request)
                }
                .collect { topicResult ->
                    topicResult.onSuccess {
                        val topic = it.topic.mapToTopicModel()
                        reduce { state.copy(isLoading = false, topicModel = topic) }
                    }
                    topicResult.onFailure {
                        reduce { state.copy(isLoading = false, exception = it) }
                    }
                }
        }
    }

    private fun selectPhrase(phraseId: Int) = intent {
        postSideEffect(sideEffect = TopicSideEffect.NavigateToPhrase(phraseId))
    }

    private fun backToDictionary() = intent {
        postSideEffect(sideEffect = TopicSideEffect.BackToDictionary())
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val topicUseCase: Provider<GetTopicUseCase>,
        private val tranLangUseCase: Provider<GetTranslationLanguageUseCase>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TopicViewModel::class.java)
            return TopicViewModel(
                topicUseCase.get(),
                tranLangUseCase.get(),
            ) as T
        }
    }
}

@Immutable
data class TopicUiState(
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
    val topicModel: TopicModel? = null
)

sealed class TopicEvent {
    data class SelectPhrase(val phraseId: Int) : TopicEvent()
    class BackToDictionary() : TopicEvent()
}

sealed class TopicSideEffect {
    data class NavigateToPhrase(val phraseId: Int) : TopicSideEffect()
    class BackToDictionary() : TopicSideEffect()
}