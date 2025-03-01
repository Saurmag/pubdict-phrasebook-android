package com.example.presentation_dictionary.design

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.presentation_common.design.Error
import com.example.presentation_common.design.Loading
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.phrasebook.Phrasebook
import com.example.presentation_dictionary.phrasebook.PhrasebookUiState
import com.example.presentation_dictionary.word.list.WordList
import com.example.presentation_dictionary.word.list.WordModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DictionaryTabs(
    wordsUiState: LazyPagingItems<WordModel>,
    phrasebookUiState: PhrasebookUiState,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberSaveable { mutableStateOf(TabRowState.Dictionary) }
    var tabIsClicked by remember { mutableStateOf(false) }
    val indicator =
        @Composable { tabPositions: List<TabPosition> ->
            TabRowIndicator(
                modifier = Modifier.tabCustomOffsetIndicator(tabPositions[state.value.ordinal])
            )
        }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = state.value.ordinal,
            indicator = indicator,
            divider = {
                HorizontalDivider(color = Color(0x19000000))
            },
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            DictionaryTab(
                tabText = R.string.dictionary_tab,
                selected = state.value == TabRowState.Dictionary,
                onTabClick = {
                    state.value = TabRowState.Dictionary; tabIsClicked = true
                }
            )
            DictionaryTab(
                tabText = R.string.phrasebook_tab,
                selected = state.value == TabRowState.Phrasebook,
                onTabClick = {
                    state.value = TabRowState.Phrasebook; tabIsClicked = true
                }
            )
        }
        TabsContent(
            wordsUiState = wordsUiState,
            phrasebookUiState = phrasebookUiState,
            tabRowState = state,
            onTopicClick = onTopicClick,
            onWordClick = onWordClick
        )
    }
}

@Composable
fun DictionaryTab(
    @StringRes tabText: Int,
    selected: Boolean,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Tab(
        selected = selected,
        onClick = onTabClick,
        selectedContentColor = Color.Black,
        unselectedContentColor = Color.Black,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = tabText),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun TabRowIndicator(
    modifier: Modifier
) {
    Box(
        modifier
            .requiredWidth(80.dp)
            .height(2.dp)
            .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(5.dp))
    )
}

@Composable
fun TabsContent(
    wordsUiState: LazyPagingItems<WordModel>,
    phrasebookUiState: PhrasebookUiState,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    tabRowState: MutableState<TabRowState>,
) {
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val widthPx = with(density) { screenWidthDp.toPx() }
    val animatableSaver = Saver<Animatable<Float, AnimationVector1D>, Float>(
        save = { it.value },
        restore = { Animatable(it) }
    )
    val animatableOffsetX = rememberSaveable(saver = animatableSaver) { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        snapshotFlow { tabRowState.value }
            .collect { state ->
                if (state == TabRowState.Dictionary) {
                    animatableOffsetX.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 250)
                    )
                }
                else {
                    animatableOffsetX.animateTo(
                        targetValue = -widthPx,
                        animationSpec = tween(durationMillis = 250)
                    )
                }
            }
    }
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId(TabRowState.Dictionary)
            ) {
                when (wordsUiState.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        WordList(
                            words = wordsUiState,
                            onWordClick = onWordClick,
                        )
                    }

                    is LoadState.Loading -> {
                        Loading()
                    }

                    is LoadState.Error -> {
                        (wordsUiState.loadState.refresh as LoadState.Error).error.localizedMessage?.let {
                            Error(errorMessage = it)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(TabRowState.Phrasebook)
            ) {
                when {
                    phrasebookUiState.isLoading -> Loading()

                    phrasebookUiState.exception != null -> {
                        phrasebookUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                    }

                    phrasebookUiState.topicList.isNotEmpty() -> {
                        Phrasebook(
                            topics = phrasebookUiState.topicList,
                            onTopicClick = onTopicClick,
                            modifier = Modifier
                        )
                    }
                }
            }
        },
        measurePolicy = dictionaryTabPager(),
        modifier = Modifier
            .offset { IntOffset(x = animatableOffsetX.value.roundToInt(), y = 0) }
    )
}

fun Modifier.tabCustomOffsetIndicator(currentTabPosition: TabPosition) =
    composed {
        val animatableWidth = remember {
            Animatable(
                initialValue = currentTabPosition.width.value
            )
        }
        val animatableOffset = remember {
            Animatable(initialValue = currentTabPosition.left.value)
        }
        LaunchedEffect(currentTabPosition) {
            launch {
                animatableWidth.animateTo(
                    targetValue = currentTabPosition.width.value,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            }
            launch {
                animatableOffset.animateTo(
                    targetValue = currentTabPosition.left.value,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
            }
        }
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset { IntOffset(x = animatableOffset.value.dp.roundToPx(), y = 0) }
            .width(animatableWidth.value.dp)
    }

fun dictionaryTabPager(): MeasureScope.(measurables: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val dictionaryPlaceable = measurables.first { it.layoutId == TabRowState.Dictionary }
            .measure(constraints)
        val phrasebookPlaceable = measurables.first { it.layoutId == TabRowState.Phrasebook }
            .measure(constraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            dictionaryPlaceable.place(constraints.minWidth, 0)
            phrasebookPlaceable.place(constraints.maxWidth, 0)
        }
    }
}

enum class TabRowState {
    Dictionary, Phrasebook
}