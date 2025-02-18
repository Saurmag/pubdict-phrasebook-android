package com.example.presentation_dictionary.dictionary

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.presentation_common.design.DictionaryLayoutWithDraw
import com.example.presentation_common.design.Error
import com.example.presentation_common.design.Loading
import com.example.presentation_common.design.LocalGradientColors
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_common.design.showToast
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.design.DictionarySearchBar
import com.example.presentation_dictionary.design.DictionaryTabs
import com.example.presentation_dictionary.design.language_top_bar.LanguageConfigurationTopBar
import com.example.presentation_dictionary.design.swipeablecard.WordsOfDayCardPager
import com.example.presentation_dictionary.phrasebook.PhrasebookUiState
import com.example.presentation_dictionary.word.list.WordModel
import com.example.presentation_dictionary.words_of_day.ImageWordOfDayModel
import com.example.presentation_dictionary.words_of_day.WordOfDayModel
import com.example.presentation_dictionary.words_of_day.WordOfDayUiState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DictionaryScreen(
    dictionaryViewModel: DictionaryViewModel,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit
) {
    val dictionaryUiState by dictionaryViewModel.collectAsState()
    val wordsPagingSource = dictionaryUiState.wordsPageFlow.collectAsLazyPagingItems()
    DictionaryScreen(
        tranLanguagesUiState = dictionaryUiState.tranLanguages,
        translationLanguageUiState = dictionaryUiState.tranLanguage,
        wordOfDayUiState = dictionaryUiState.wordsOfDay,
        wordsUiState = wordsPagingSource,
        phrasebookUiState = dictionaryUiState.phrasebook,
        searchQueryState = dictionaryUiState.searchQuery,
        onCardClick = { dictionaryViewModel.handleEvent(event = DictionaryEvent.SelectWord(it)) },
        onShareIconClick = { dictionaryViewModel.handleEvent(event = DictionaryEvent.ShareWord(it)) },
        onWordClick = { dictionaryViewModel.handleEvent(event = DictionaryEvent.SelectWord(it)) },
        onTopicClick = { dictionaryViewModel.handleEvent(event = DictionaryEvent.SelectTopic(it)) },
        onSearchQueryChange = {
            dictionaryViewModel.handleEvent(
                event = DictionaryEvent.EnterSearchQuery(
                    it
                )
            )
        },
        onUpdateTranLanguage = {
            dictionaryViewModel.handleEvent(
                event = DictionaryEvent.ChangeTranslationLanguage(
                    it
                )
            )
        },
        onSearchBarCancelIconClick = { dictionaryViewModel.handleEvent(event = DictionaryEvent.CleanSearchQuery()) }
    )
    val context = LocalContext.current
    dictionaryViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is DictionarySideEffect.NavigateToTopic -> onTopicClick(sideEffect.topicId)
            is DictionarySideEffect.NavigateToWord -> onWordClick(sideEffect.word)
            is DictionarySideEffect.ShowToast -> showToast(sideEffect.message, context)
        }
    }
}

@Composable
internal fun DictionaryScreen(
    tranLanguagesUiState: TranslationLanguagesUiState,
    translationLanguageUiState: TranslationLanguageUiState,
    wordOfDayUiState: WordOfDayUiState,
    wordsUiState: LazyPagingItems<WordModel>,
    searchQueryState: String,
    onCardClick: (String) -> Unit,
    onShareIconClick: (String) -> Unit,
    phrasebookUiState: PhrasebookUiState,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUpdateTranLanguage: (LanguageModel) -> Unit,
    onSearchBarCancelIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            when {
                translationLanguageUiState.isLoading -> Loading()

                translationLanguageUiState.exception != null -> {
                    translationLanguageUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                }

                translationLanguageUiState.language != null -> {
                    LanguageConfigurationTopBar(
                        translationLangList = tranLanguagesUiState.languages,
                        language = translationLanguageUiState.language,
                        onUpdateTranLanguage = onUpdateTranLanguage,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                    )
                }
            }
        },
        containerColor = Color.Transparent,
    ) { innerPadding ->
        DictionaryContent(
            wordOfDayUiState = wordOfDayUiState,
            wordsUiState = wordsUiState,
            phrasebookUiState = phrasebookUiState,
            searchQueryState = searchQueryState,
            onCardClick = onCardClick,
            onWordClick = onWordClick,
            onTopicClick = onTopicClick,
            onSearchQueryChange = onSearchQueryChange,
            onSearchBarCancelIconClick = onSearchBarCancelIconClick,
            onShareIconClick = onShareIconClick,
            modifier = modifier
                .consumeWindowInsets(innerPadding)
                .padding(innerPadding)
        )
    }
}

@Composable
fun DictionaryContent(
    wordOfDayUiState: WordOfDayUiState,
    wordsUiState: LazyPagingItems<WordModel>,
    phrasebookUiState: PhrasebookUiState,
    searchQueryState: String,
    onCardClick: (String) -> Unit,
    onShareIconClick: (String) -> Unit,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchBarCancelIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var contentState by rememberSaveable { mutableStateOf(DictionaryState.DictionaryHome) }
    var searchBarTrailingIconState by rememberSaveable { mutableStateOf(true) }
    DictionaryLayoutWithDraw(
        content = {
            Column {
                DictionarySearchBar(
                    query = searchQueryState,
                    onQueryChange = {
                        onSearchQueryChange(it)
                        contentState = DictionaryState.DictionaryTabs
                        searchBarTrailingIconState = false
                    },
                    onTrailingIconClick = {
                        contentState =
                            if (contentState == DictionaryState.DictionaryHome)
                                DictionaryState.DictionaryTabs
                            else
                                DictionaryState.DictionaryHome
                        if (!searchBarTrailingIconState) onSearchBarCancelIconClick()
                        searchBarTrailingIconState = !searchBarTrailingIconState
                    },
                    trailingIconState = searchBarTrailingIconState
                )
                when (contentState) {
                    DictionaryState.DictionaryHome -> {
                        DictionaryHomeContent(
                            wordOfDayUiState = wordOfDayUiState,
                            onCardClick = onCardClick,
                            onShareIconClick = onShareIconClick
                        )
                    }

                    DictionaryState.DictionaryTabs -> {
                        DictionaryTabs(
                            wordsUiState = wordsUiState,
                            queryPart = searchQueryState,
                            phrasebookUiState = phrasebookUiState,
                            onWordClick = onWordClick,
                            onTopicClick = onTopicClick
                        )
                        BackHandler {
                            onSearchBarCancelIconClick()
                            searchBarTrailingIconState = !searchBarTrailingIconState
                            contentState = DictionaryState.DictionaryHome
                        }
                    }
                }
            }
        },

        )
}

enum class DictionaryState {
    DictionaryHome, DictionaryTabs
}

@Composable
fun DictionaryHomeContent(
    wordOfDayUiState: WordOfDayUiState,
    onCardClick: (String) -> Unit,
    onShareIconClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(4f)
        ) {
            when {
                wordOfDayUiState.isLoading -> Loading()

                wordOfDayUiState.exception != null -> {
                    wordOfDayUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                }

                wordOfDayUiState.wordsOfDay.isNotEmpty() -> {
                    WordsOfDayCardPager(
                        wordsOfDay = wordOfDayUiState.wordsOfDay,
                        onCardClick = onCardClick,
                        onShareIconClick = onShareIconClick
                    )
                }
            }
        }
        TestTransition(
            modifier = Modifier.weight(3f),
            onTestTransition = {
                Toast.makeText(
                    context,
                    context.getString(R.string.not_implemented),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun TestTransition(
    onTestTransition: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .clip(RoundedCornerShape(52.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        LocalGradientColors.current.bottom,
                        LocalGradientColors.current.top
                    )
                )
            )
            .fillMaxWidth()
            .clickable { onTestTransition() }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                )
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.education_icon),
                    contentDescription = stringResource(
                        id = R.string.transiton_test,
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(id = com.example.presentation_common.R.string.transition_test_lez),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0x16, 0x16, 0x16)
                )
            }
            Text(
                text = stringResource(id = R.string.transiton_test),
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xB2161616)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(color = Color.White)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.transition_icon),
                contentDescription = stringResource(id = R.string.transiton_test),
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun DictionaryContentHomePreview() {
    val wordsOfDay = listOf(
        WordOfDayModel(0, "ваз", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "вад", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "вал", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "чизвани", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "унцукуль", ImageWordOfDayModel("", 0, 0, 0))
    )
    val context = LocalContext.current
    PubDictTheme {
        DictionaryHomeContent(
            wordOfDayUiState = WordOfDayUiState(wordsOfDay = wordsOfDay),
            onCardClick = { Toast.makeText(context, "Preview Click", Toast.LENGTH_SHORT).show() },
            onShareIconClick = {}
        )
    }
}
