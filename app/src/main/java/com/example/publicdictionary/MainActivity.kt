package com.example.publicdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data_local.injection.DaggerDataLocalComponent
import com.example.data_local.injection.DataLocalComponent
import com.example.data_remote.injection.DaggerDataRemoteComponent
import com.example.data_remote.injection.DataRemoteComponent
import com.example.data_repository.injection.DaggerDataRepositoryComponent
import com.example.data_repository.injection.DataRepositoryComponent
import com.example.presentation_common.navigation.NavRoutes
import com.example.presentation_common.navigation.PhraseInput
import com.example.presentation_common.navigation.TopicInput
import com.example.presentation_phrasebook.phrase.list.TopicScreen
import com.example.presentation_phrasebook.phrase.list.TopicViewModel
import com.example.presentation_phrasebook.phrase.single.PhraseScreen
import com.example.presentation_phrasebook.phrase.single.PhraseViewModel
import com.example.presentation_phrasebook.topic.list.TopicListScreen
import com.example.presentation_phrasebook.topic.list.TopicListViewModel
import com.example.presentation_word_of_day.words_of_day.WordsOfDayScreen
import com.example.presentation_word_of_day.words_of_day.WordsOfDayViewModel
import com.example.publicdictionary.injection.AppComponent
import com.example.publicdictionary.injection.DaggerAppComponent
import dagger.Lazy
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val dataLocalComponent: DataLocalComponent by lazy {
        DaggerDataLocalComponent
            .builder()
            .context(applicationContext)
            .build()
    }

    private val dataRemoteComponent: DataRemoteComponent by lazy {
        DaggerDataRemoteComponent.create()
    }

    private val dataRepositoryComponent: DataRepositoryComponent by lazy {
        DaggerDataRepositoryComponent.builder()
            .depsRemotePhrasebookDataSource(dataRemoteComponent)
            .build()
    }

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    @Inject
    lateinit var topicListViewModelFactory: Lazy<TopicListViewModel.Factory>

    private val topicListViewModel: TopicListViewModel by viewModels {
        topicListViewModelFactory.get()
    }

    @Inject
    lateinit var topicViewModelFactory: Lazy<TopicViewModel.Factory>

    private val topicViewModel: TopicViewModel by viewModels {
        topicViewModelFactory.get()
    }

    @Inject
    lateinit var phraseViewModelFactory: Lazy<PhraseViewModel.Factory>

    private val phraseViewModel: PhraseViewModel by viewModels {
        phraseViewModelFactory.get()
    }

    @Inject
    lateinit var wordsOfDayViewModelFactory: Lazy<WordsOfDayViewModel.Factory>

    private val wordsOfDayViewModel: WordsOfDayViewModel by viewModels {
        wordsOfDayViewModelFactory.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _appComponent = DaggerAppComponent.builder()
            .depsDataLocalComponent(dataLocalComponent)
            .depsDataRemoteComponent(dataRemoteComponent)
            .depsDataRepositoryComponent(dataRepositoryComponent)
            .build()

        appComponent.inject(this)

        enableEdgeToEdge()
        setContent {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0x2C, 0xDD, 0x79),
                                Color(0x11, 0xFF, 0xA9)
                            )
                        )
                    )
            ) {
                val navController = rememberNavController()
                App(
                    navController = navController,
                    topicListViewModel = topicListViewModel,
                    topicViewModel = topicViewModel,
                    phraseViewModel = phraseViewModel,
                    wordsOfDayViewModel = wordsOfDayViewModel
                )
            }
        }
    }
}

@Composable
fun App(
    navController: NavHostController,
    wordsOfDayViewModel: WordsOfDayViewModel,
    topicListViewModel: TopicListViewModel,
    topicViewModel: TopicViewModel,
    phraseViewModel: PhraseViewModel
) {
    NavHost(navController = navController, startDestination = NavRoutes.WordsOfDay.route) {
        composable(route = NavRoutes.WordsOfDay.route) {
            wordsOfDayViewModel.loadWordsOfDay()
            val wordsOfDayUiState = wordsOfDayViewModel.wordsOfDayFlow.collectAsStateWithLifecycle().value
            WordsOfDayScreen(wordsOfDayUiState = wordsOfDayUiState) {
                navController.navigate(NavRoutes.Phrasebook.route)
            }
        }
        composable(route = NavRoutes.Phrasebook.route) {
            topicListViewModel.loadTopicList()
            val topicsUiState = topicListViewModel.topicListFlow.collectAsStateWithLifecycle().value
            TopicListScreen(topicsUiState = topicsUiState) { topicListItemModel ->
                navController.navigate(NavRoutes.Topic.routeForTopic(TopicInput(id = topicListItemModel.id)))
            }
        }
        composable(
            route = NavRoutes.Topic.route,
            arguments = NavRoutes.Topic.arguments
        ) {
            val topicInput = NavRoutes.Topic.fromEntry(it)
            topicViewModel.getTopic(topicId = topicInput.id)
            val topicUiState = topicViewModel.topicFlow.collectAsStateWithLifecycle().value
            TopicScreen(topicUiState = topicUiState) { phraseListItemModel ->  
                navController.navigate(NavRoutes.Phrase.routeForPhrase(PhraseInput(id = phraseListItemModel.id)))
            }
        }
        composable(
            route = NavRoutes.Phrase.route,
            arguments = NavRoutes.Phrase.arguments
        ) {
            val phraseInput = NavRoutes.Phrase.fromEntry(it)
            phraseViewModel.loadPhrase(phraseId = phraseInput.id)
            val phraseUiState = phraseViewModel.phraseFlow.collectAsStateWithLifecycle().value
            PhraseScreen(phraseUiState = phraseUiState)
        }
    }
}

