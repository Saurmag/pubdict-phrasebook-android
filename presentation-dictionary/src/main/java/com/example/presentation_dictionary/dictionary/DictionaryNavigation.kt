package com.example.presentation_dictionary.dictionary

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.presentation_common.navigation.daggerViewModel
import com.example.presentation_dictionary.injection.DictionaryComponentViewModel
import com.example.presentation_dictionary.phrase.phraseScreen
import com.example.presentation_dictionary.topic.topicScreen
import com.example.presentation_dictionary.word.single.wordScreen
import dagger.Lazy
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data object DictionaryBaseRoute

@Serializable
data object DictionaryRoute

class DictionaryNavigation {
    @Inject
    lateinit var viewModelFactory: Lazy<DictionaryViewModel.Factory>

    @Composable
    fun dictionaryViewModel(entry: NavBackStackEntry): DictionaryViewModel {
        ViewModelProvider(owner = entry)[DictionaryComponentViewModel::class].dictionaryComponent.inject(this)
        return daggerViewModel<DictionaryViewModel>(
            owner = entry,
            factory = viewModelFactory.get()
        )
    }
}

fun NavController.navigateToDictionary(navOptions: NavOptions) =
    navigate(route = DictionaryRoute, navOptions)

fun NavGraphBuilder.dictionaryScreen(
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
) {
    composable<DictionaryRoute>(
        enterTransition = null,
        exitTransition = null,
        popEnterTransition = {
            fadeIn(animationSpec = tween(50))
        },
    ) {
        val dictionaryViewModel = DictionaryNavigation().dictionaryViewModel(it)
        DictionaryScreen(
            dictionaryViewModel = dictionaryViewModel,
            onWordClick = onWordClick,
            onTopicClick = onTopicClick
        )
    }
}


fun NavGraphBuilder.dictionaryComponent(
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    onPhraseClick: (Int) -> Unit,
    onBackClick: () -> Boolean
) {
    navigation<DictionaryBaseRoute>(startDestination = DictionaryRoute) {
        dictionaryScreen(
            onWordClick = onWordClick,
            onTopicClick = onTopicClick
        )
        topicScreen(
            onPhraseClick = onPhraseClick,
            onBackClick = onBackClick
        )
        phraseScreen { onBackClick() }
        wordScreen { onBackClick() }
    }
}
