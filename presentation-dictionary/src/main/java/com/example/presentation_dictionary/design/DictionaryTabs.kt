package com.example.presentation_dictionary.design

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun DictionaryTabs(
    wordsUiState: LazyPagingItems<WordModel>,
    phrasebookUiState: PhrasebookUiState,
    onWordClick: (String) -> Unit,
    onTopicClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var state by rememberSaveable { mutableStateOf(TabRowState.Dictionary) }
    var tabIsClicked by remember { mutableStateOf(false) }
    val indicator =
        @Composable { tabPositions: List<TabPosition> ->
            TabRowIndicator(
                modifier = Modifier.tabCustomOffsetIndicator(tabPositions[state.ordinal])
            )
        }
    var pointEventEnabled by remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = state.ordinal,
            indicator = indicator,
            divider = {
                HorizontalDivider(color = Color(0x19000000))
            },
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            DictionaryTab(
                tabText = R.string.dictionary_tab,
                selected = state == TabRowState.Dictionary,
                onTabClick = { state = TabRowState.Dictionary; tabIsClicked = true }
            )
            DictionaryTab(
                tabText = R.string.phrasebook_tab,
                selected = state == TabRowState.Phrasebook,
                onTabClick = { state = TabRowState.Phrasebook; tabIsClicked = true }
            )
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
                                pointEventEnabled = pointEventEnabled,
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
                                pointEventEnabled = pointEventEnabled,
                                modifier = Modifier
                            )
                        }
                    }
                }
            },
            measurePolicy = dictionaryTabPager(),
            modifier = Modifier
                .swipeToChangeTab(
                    tabRowState = state,
                    tabIsClicked = tabIsClicked,
                    tabChanged = { tabIsClicked = false },
                    onSwiped = { pointEventEnabled = it },
                    onDictionarySwipe = { state = TabRowState.Phrasebook },
                    onPhrasebookSwipe = { state = TabRowState.Dictionary }
                )
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

fun Modifier.swipeToChangeTab(
    tabRowState: TabRowState,
    tabIsClicked: Boolean,
    tabChanged: (Boolean) -> Unit,
    onSwiped: (Boolean) -> Unit,
    onDictionarySwipe: () -> Unit,
    onPhrasebookSwipe: () -> Unit
): Modifier = composed {
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val widthPx = with(density) { screenWidthDp.toPx() }
    val animatableSaver = Saver<Animatable<Float, AnimationVector1D>, Float>(
        save = { it.value },
        restore = { Animatable(it) }
    )
    val animatableOffsetX = rememberSaveable(saver = animatableSaver) { Animatable(0f) }
    val swipeStateDictionary = tabRowState == TabRowState.Dictionary
    val swipeStatePhrasebook = tabRowState == TabRowState.Phrasebook
    LaunchedEffect(tabIsClicked) {
        if (tabIsClicked) {
            val targetValue = if (tabRowState != TabRowState.Dictionary) -widthPx else 0f
            animatableOffsetX.animateTo(
                targetValue = targetValue,
                animationSpec = tween(200)
            )
            tabChanged(false)
        }
    }
    pointerInput(swipeStateDictionary) {
        if (swipeStateDictionary) {
            val decay = splineBasedDecay<Float>(this)
            coroutineScope {
                while (true) {
                    val velocityTracker = VelocityTracker()
                    animatableOffsetX.stop()
                    awaitPointerEventScope {
                        val pointerId = awaitFirstDown(
                            requireUnconsumed = false,
                            pass = PointerEventPass.Initial
                        )
                        horizontalDrag(pointerId = pointerId.id) { change ->
                            if (change.pressed && change.positionChange().y in -20f..20f) {
                                onSwiped(false)
                            }
                            if (animatableOffsetX.isRunning) {
                                change.consume()
                            }
                            launch {
                                val targetValue =
                                    if (animatableOffsetX.value + change.positionChange().x > 0) 0f
                                    else animatableOffsetX.value + change.positionChange().x
                                animatableOffsetX.snapTo(
                                    targetValue = targetValue
                                )
                            }
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                change.position
                            )
                        }
                    }
                    val velocity = tabsNormalizeVelocity(
                        velocity = velocityTracker.calculateVelocity().x,
                        tabRowState = tabRowState
                    )
                    val targetOffsetX = decay.calculateTargetValue(
                        initialValue = animatableOffsetX.value,
                        initialVelocity = velocity,
                    )
                    animatableOffsetX.updateBounds(
                        lowerBound = -widthPx,
                        upperBound = 0f
                    )
                    launch {
                        if (velocity != 0f) {
                            animatableOffsetX.animateDecay(
                                initialVelocity = velocity,
                                animationSpec = decay
                            )
                            onSwiped(false)
                            onDictionarySwipe()
                        } else {
                            if (targetOffsetX.absoluteValue <= widthPx * 0.7) {
                                animatableOffsetX.animateTo(
                                    targetValue = 0f,
                                    initialVelocity = velocity,
                                    animationSpec = tween(durationMillis = 200)
                                )
                                onSwiped(true)
                            } else {
                                val targetValueOffsetX = -widthPx
                                animatableOffsetX.animateTo(
                                    targetValue = targetValueOffsetX,
                                    animationSpec = tween(durationMillis = 200)
                                )
                                onSwiped(true)
                                onDictionarySwipe()
                            }
                        }
                    }
                }
            }
        }
    }
        .pointerInput(swipeStatePhrasebook) {
            if (swipeStatePhrasebook) {
                val decay = splineBasedDecay<Float>(this)
                coroutineScope {
                    while (true) {
                        val velocityTracker = VelocityTracker()
                        animatableOffsetX.stop()
                        awaitPointerEventScope {
                            val pointerId = awaitFirstDown(
                                requireUnconsumed = false,
                                pass = PointerEventPass.Initial
                            )
                            horizontalDrag(pointerId = pointerId.id) { change ->
                                if (change.pressed && change.positionChange().y in -3f..3f) {
                                    onSwiped(false)
                                }
                                if (animatableOffsetX.isRunning) {
                                    change.consume()
                                }
                                launch {
                                    val targetValue =
                                        if (animatableOffsetX.value + change.positionChange().x < -widthPx) -widthPx
                                        else animatableOffsetX.value + change.positionChange().x
                                    animatableOffsetX.snapTo(
                                        targetValue = targetValue
                                    )
                                }
                                velocityTracker.addPosition(
                                    change.uptimeMillis,
                                    change.position
                                )
                            }
                        }
                        val velocity = tabsNormalizeVelocity(
                            velocity = velocityTracker.calculateVelocity().x,
                            tabRowState = tabRowState
                        )
                        val targetOffsetX = decay.calculateTargetValue(
                            initialValue = animatableOffsetX.value,
                            initialVelocity = velocity,
                        )
                        animatableOffsetX.updateBounds(
                            lowerBound = -widthPx,
                            upperBound = 0f
                        )
                        launch {
                            if (velocity != 0f) {
                                animatableOffsetX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                                onSwiped(true)
                                onPhrasebookSwipe()
                            } else {
                                if (targetOffsetX.absoluteValue >= widthPx * 0.7) {
                                    animatableOffsetX.animateTo(
                                        targetValue = -widthPx,
                                        initialVelocity = velocity,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                    onSwiped(true)
                                } else {
                                    val targetValueOffsetX = 0f
                                    animatableOffsetX.animateTo(
                                        targetValue = targetValueOffsetX,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                    onSwiped(true)
                                    onPhrasebookSwipe()
                                }
                            }
                        }
                    }
                }
            }
        }
        .offset { IntOffset(x = animatableOffsetX.value.roundToInt(), y = 0) }
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

fun tabsNormalizeVelocity(velocity: Float, tabRowState: TabRowState): Float {
    val normalizeVelocity: Float
    when (tabRowState) {
        TabRowState.Dictionary -> {
            normalizeVelocity = when (if (velocity < 0) velocity else 0f) {
                in -1000f..0f -> 0f
                in -2000f..-1001f -> -6000f
                in -5000f..-2001f -> -8000f
                in -10000f..-5001f -> -10000f
                in -15000f..-10001f -> -12000f
                in -20000f..-15001f -> -13000f
                else -> -14000f
            }
        }

        TabRowState.Phrasebook -> {
            normalizeVelocity = when (if (velocity > 0) velocity else 0f) {
                in 0f..1000f -> 0f
                in 1001f..2000f -> 6000f
                in 2001f..5000f -> 8000f
                in 5001f..10000f -> 10000f
                in 10001f..15000f -> 12000f
                in 15001f..20000f -> 13000f
                else -> 14000f
            }
        }
    }
    return normalizeVelocity
}

enum class TabRowState {
    Dictionary, Phrasebook
}