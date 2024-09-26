package com.example.presentation_phrasebook.phrase.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTopicUseCase
import com.example.presentation_common.ORIGIN_LANGUAGE_ISO
import com.example.presentation_common.TranslatedLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class TopicViewModel(
    private val topicUseCase: GetTopicUseCase,
    private val converter: TopicConverter
): ViewModel() {

    private var _topicFlow: MutableStateFlow<TopicUiState> =
        MutableStateFlow(TopicUiState(isLoading = true))

    val topicFlow: StateFlow<TopicUiState>
        get() = _topicFlow.asStateFlow()

    fun getTopic(topicId: Int) {
        viewModelScope.launch {
            topicUseCase.execute(
                request = GetTopicUseCase.Request(
                    topicId = topicId,
                    srcLangIso = ORIGIN_LANGUAGE_ISO,
                    tarLangIso = TranslatedLanguage.RUSSIA.iso
                )
            ).map {
                    converter.convert(it)
            }.collect {
                _topicFlow.value = it
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val topicUseCase: Provider<GetTopicUseCase>,
        private val converter: Provider<TopicConverter>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TopicViewModel::class.java)
            return TopicViewModel(topicUseCase.get(), converter.get()) as T
        }
    }
}