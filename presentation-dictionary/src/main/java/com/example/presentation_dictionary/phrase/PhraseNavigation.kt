package com.example.presentation_dictionary.phrase

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

class PhraseNavigation {
    @Inject
    lateinit var viewModelAssistedFactory: PhraseViewModelAssistedFactory

    @Composable
    fun phraseViewModel(entry: NavBackStackEntry, savedStateHandle: SavedStateHandle): PhraseViewModel {
        ViewModelProvider(owner = entry)[DictionaryComponentViewModel::class].dictionaryComponent.inject(this)
        val viewModelFactory = viewModelAssistedFactory.create(savedStateHandle)
        return daggerViewModel<PhraseViewModel>(
            owner = entry,
            factory = viewModelFactory
        )
    }
}

@Serializable
data class PhraseRoute(val phraseId: Int)

fun NavController.navigateToPhrase(phraseId: Int, navOptions: NavOptions? = null) =
    navigate(route = PhraseRoute(phraseId = phraseId), navOptions)

fun NavGraphBuilder.phraseScreen(onBackClick: () -> Unit) {
    composable<PhraseRoute>(
        enterTransition = {
            slideInHorizontally(animationSpec = tween(300)) { fullWidth ->
                fullWidth / 4
            } + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally { fullWidth ->
                fullWidth
            }
        },
        popExitTransition = {
            slideOutHorizontally { fullWidth ->
                fullWidth
            }
        },
        popEnterTransition = null
    ) { entry ->
        val phraseId = entry.toRoute<PhraseRoute>().phraseId
        entry.savedStateHandle.set(key = "phraseId", value = phraseId)
        val phraseViewModel = PhraseNavigation().phraseViewModel(entry, entry.savedStateHandle)
        PhraseScreen(
            viewModel = phraseViewModel,
            onBackClick = onBackClick
        )
    }
}