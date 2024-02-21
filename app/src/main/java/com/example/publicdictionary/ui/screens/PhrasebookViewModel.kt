package com.example.publicdictionary.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.publicdictionary.PublicDictionaryApplication
import com.example.publicdictionary.data.PhrasebookRepository
import com.example.publicdictionary.ui.model.Phrasebook
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

data class PhrasebookUiState(
    val isLoading: Boolean = false,
    val phrasebook: Phrasebook? = null
)

class PhrasebookViewModel(
    private val phrasebookRepository: PhrasebookRepository
) : ViewModel() {

    var phrasebookUiState by mutableStateOf(PhrasebookUiState())
        private set

    init {
        getPhrasebook()
    }

    fun getPhrasebook() {
        viewModelScope.launch {
            phrasebookUiState = PhrasebookUiState()
            phrasebookUiState = try {
                PhrasebookUiState(phrasebook = phrasebookRepository.getPhrasebook())
            } catch (e: IOException) {
                Log.e("YOUR_APP_LOG_TAG", "I got an error", e)
                PhrasebookUiState()
            } catch (e: HttpException) {
                Log.e("YOUR_APP_LOG_TAG", "I got an error", e)
                PhrasebookUiState()
            }
        }
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