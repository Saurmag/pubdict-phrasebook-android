package com.example.presentation_dictionary.word.single

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.presentation_common.navigation.daggerViewModel
import com.example.presentation_dictionary.injection.DictionaryComponentViewModel
import kotlinx.serialization.Serializable
import javax.inject.Inject

class WordNavigation {
    @Inject
    lateinit var viewModelAssistedFactory: DetailWordViewModelAssistedFactory

    @Composable
    fun wordViewModel(entry: NavBackStackEntry, savedStateHandle: SavedStateHandle): DetailWordViewModel {
        ViewModelProvider(owner = entry)[DictionaryComponentViewModel::class].dictionaryComponent.inject(this)
        val viewModelFactory = viewModelAssistedFactory.create(savedStateHandle)
        return daggerViewModel<DetailWordViewModel>(
            owner = entry,
            factory = viewModelFactory
        )
    }
}

@Serializable
data class WordRoute(val word: String)

fun NavController.navigateToWord(word: String, navOptions: NavOptions? = null) =
    navigate(route = WordRoute(word = word), navOptions)

fun NavGraphBuilder.wordScreen(
    onBackClick: () -> Unit
) {
    composable<WordRoute>(
        enterTransition = {
            slideInHorizontally(animationSpec = tween(300)) { fullWidth ->
                fullWidth / 4
            } + fadeIn(animationSpec = tween(300))
        },
        exitTransition = null,
        popEnterTransition = null,
        popExitTransition = {
            slideOutHorizontally { fullWidth ->
                fullWidth
            }
        }
    ) { entry ->
        val word = entry.toRoute<WordRoute>().word
        entry.savedStateHandle.set("word", word)
        val wordViewModel = WordNavigation().wordViewModel(entry, entry.savedStateHandle)
        WordScreen(
            viewModel = wordViewModel,
            onBackClick = onBackClick
        )
    }
}