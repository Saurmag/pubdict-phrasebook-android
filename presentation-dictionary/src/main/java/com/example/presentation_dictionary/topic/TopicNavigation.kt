package com.example.presentation_dictionary.topic

 import androidx.compose.animation.core.tween
 import androidx.compose.animation.fadeIn
 import androidx.compose.animation.slideInHorizontally
 import androidx.compose.animation.slideOutHorizontally
 import androidx.compose.runtime.Composable
 import androidx.lifecycle.ViewModelProvider
 import androidx.navigation.NavBackStackEntry
 import androidx.navigation.NavController
 import androidx.navigation.NavGraphBuilder
 import androidx.navigation.NavOptions
 import androidx.navigation.compose.composable
 import androidx.navigation.toRoute
 import com.example.presentation_common.navigation.daggerViewModel
 import com.example.presentation_dictionary.injection.DictionaryComponentViewModel
 import dagger.Lazy
 import kotlinx.serialization.Serializable
 import javax.inject.Inject

class TopicNavigation {
    @Inject
    lateinit var viewModelFactory: Lazy<TopicViewModel.Factory>

    @Composable
    fun topicViewModel(entry: NavBackStackEntry): TopicViewModel {
        ViewModelProvider(owner = entry)[DictionaryComponentViewModel::class].dictionaryComponent.inject(this)
        return daggerViewModel<TopicViewModel>(
            owner = entry,
            factory = viewModelFactory.get()
        )
    }
}

@Serializable
data class TopicRoute(
    val topicId: Int
)

fun NavController.navigateToTopic(topicId: Int, navOptions: NavOptions? = null) =
    navigate(route = TopicRoute(topicId = topicId), navOptions)

fun NavGraphBuilder.topicScreen(
    onPhraseClick: (Int) -> Unit,
    onBackClick: () -> Boolean
) {
    composable<TopicRoute>(
        enterTransition = {
            slideInHorizontally(animationSpec = tween(300)) { fullWidth ->
                fullWidth / 4
            } + fadeIn(animationSpec = tween(300))
        },
        exitTransition = null,
        popEnterTransition = {
            fadeIn(animationSpec = tween(50))
        },
        popExitTransition = {
            slideOutHorizontally { fullWidth ->
                fullWidth
            }
        }
    ) { entry ->
        val topicId = entry.toRoute<TopicRoute>().topicId
        val topicViewModel = TopicNavigation().topicViewModel(entry)
        topicViewModel.getTopic(topicId = topicId)
        TopicScreen(
            viewModel = topicViewModel,
            onPhraseClick = onPhraseClick,
            onBackClick = { onBackClick() }
        )
    }
}