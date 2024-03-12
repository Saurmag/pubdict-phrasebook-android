package com.example.publicdictionary.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.publicdictionary.PublicDictionaryApplication
import com.example.publicdictionary.data.PhrasebookRepository
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.model.Phrasebook
import com.example.publicdictionary.ui.model.Topic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PhrasebookUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val phrasebook: Phrasebook? = null
)

class PhrasebookViewModel(
    private val phrasebookRepository: PhrasebookRepository
) : ViewModel() {

    private val _phrasebookUiState = MutableStateFlow(PhrasebookUiState())
    val phrasebookUiState: StateFlow<PhrasebookUiState> = _phrasebookUiState.asStateFlow()

    init {
        getPhrasebook()
    }

    fun getPhrasebook() {
        viewModelScope.launch {
            phrasebookRepository.getPhrasebook().collect {
                _phrasebookUiState.value = PhrasebookUiState(
                    isLoading = false,
                    phrasebook = it
                )
            }
        }
    }

    fun loadTopic(topicId: Int): Topic {
        val topicList = phrasebookUiState.value.phrasebook?.topics ?: listOf()
        var _topic: Topic? = null
        for (topic in topicList) {
            if (topic.id == topicId) _topic = topic
        }
        return checkNotNull(_topic)
    }

    fun loadPhrase(phraseId: Int): Phrase {
        val phrasesList = phrasebookUiState.value.phrasebook?.topics?.flatMap {
            it.phrases
        } ?: listOf()
        var _phrase: Phrase? = null
        for (phrase in phrasesList) {
            if (phrase.id == phraseId) _phrase = phrase
        }
        return checkNotNull(_phrase)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PublicDictionaryApplication)
                val phrasebookRepository = application.container.phrasebookRepository
                PhrasebookViewModel(phrasebookRepository)
            }
        }
    }
}