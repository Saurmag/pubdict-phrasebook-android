package com.example.presentation_phrasebook.topic.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTopicListUseCase
import com.example.presentation_common.ORIGIN_LANGUAGE_ISO
import com.example.presentation_common.TranslatedLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class TopicListViewModel (
    private val useCase: GetTopicListUseCase,
    private val converter: TopicListConverter
) : ViewModel() {

    private val _topicListFlow = MutableStateFlow<TopicsUiState>(TopicsUiState(
        isLoading = true
    ))
    val topicListFlow: StateFlow<TopicsUiState>
        get() = _topicListFlow.asStateFlow()

    fun loadTopicList() {
        viewModelScope.launch {
            useCase.execute(
                request = GetTopicListUseCase.Request(
                    srcLangIso = ORIGIN_LANGUAGE_ISO,
                    tarLangIso = TranslatedLanguage.RUSSIA.iso
                )
            ).map { converter.convert(it) }
                .collect { _topicListFlow.value = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val useCase: Provider<GetTopicListUseCase>,
        private val converter: Provider<TopicListConverter>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TopicListViewModel::class.java)
            return TopicListViewModel(useCase.get(), converter.get()) as T
        }
    }
}