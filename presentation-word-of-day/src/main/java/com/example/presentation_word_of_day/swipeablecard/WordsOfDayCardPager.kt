package com.example.presentation_word_of_day.swipeablecard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presentation_phrasebook.R
import com.example.presentation_word_of_day.words_of_day.WordOfDayModel
import kotlin.math.roundToInt

@Composable
fun WordsOfDayCardPager(
    wordsOfDay: List<WordOfDayModel>,
    modifier: Modifier = Modifier,
) {
    var currentPosition by rememberSaveable { mutableStateOf(CardListPosition.CURRENT_ITEM) }
    var lastPlaceable by rememberSaveable { mutableIntStateOf(wordsOfDay.size * 10) }
    var swipe by rememberSaveable { mutableIntStateOf(0) }
    val cycleEnd by rememberSaveable { mutableIntStateOf(wordsOfDay.size * 10) }
    CardPager(
        items = wordsOfDay,
        modifier = modifier.fillMaxSize(),
        lastPlaceable = lastPlaceable,
        itemContent = {
            for (index in cycleEnd downTo 0) {
                currentPosition = when(index) {
                    swipe -> CardListPosition.CURRENT_ITEM
                    swipe + 1 -> CardListPosition.NEXT_ITEM
                    swipe + 2 -> CardListPosition.POST_NEXT
                    else -> { CardListPosition.INVISIBLE }
                }
                val calculateIndex = index % wordsOfDay.size
                ExpWordOfDayCard(
                    wordOfDayModel = wordsOfDay[calculateIndex],
                    modifier = Modifier
                        .padding(16.dp)
                        .size(450.dp),
                    swipeEnabled = currentPosition == CardListPosition.CURRENT_ITEM,
                    onSwiped = { target ->
                        when(target) {
                            SwipeCardAnchor.Start -> Unit
                            else -> {
                                lastPlaceable -= 1
                                swipe += 1
                            }
                        }
                    },
                    cardListPosition = currentPosition,
                )
            }
        }
    )
}

@Composable
private fun <T> CardPager(
    items: List<T>,
    itemContent: @Composable (element: List<T>) -> Unit,
    lastPlaceable: Int,
    modifier: Modifier = Modifier,
) {
    SubcomposeLayout { constraints ->
        val currentItemsMeasurables = subcompose(
            slotId = null,
            content = { itemContent(items) }
        )
        val currentItemsPlaceables = currentItemsMeasurables.map {
            it.measure(constraints)
        }
        val layoutWidth = constraints.maxWidth
        val layoutHeight = (currentItemsPlaceables.first().height * 1.5).roundToInt()
        layout(
            width = layoutWidth,
            height = layoutHeight,
        ) {
            currentItemsPlaceables.forEachIndexed { index, placeable ->
                val xPosition = (constraints.maxWidth - placeable.width) / 2
                val yFrontPosition = 120
                val yNextPosition = 76
                val yPostNextPosition = 40
                val yInvisiblePosition = 40
                when (index) {
                    lastPlaceable -> {
                        placeable.place(xPosition, yFrontPosition)
                    }

                    lastPlaceable - 1 -> {
                        placeable.place(xPosition, yNextPosition)
                    }

                    lastPlaceable - 2 -> {
                        placeable.place(xPosition, yPostNextPosition)
                    }

                    lastPlaceable - 3 -> {
                        placeable.place(xPosition, yInvisiblePosition)
                    }
                }
            }
        }
    }
}

@Composable
internal fun ExpWordOfDayCard(
    wordOfDayModel: WordOfDayModel,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true,
    cardListPosition: CardListPosition,
    onSwiped: (target: SwipeCardAnchor) -> Unit
) {
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val swipeableState = rememberSwipeableButtonState(
        initialValue = SwipeCardAnchor.Start,
        velocityThreshold = { with(density) { screenWidthDp.toPx() } }
    )
    val cardSize = when (cardListPosition) {
        CardListPosition.CURRENT_ITEM -> SwipeableSize.Default
        CardListPosition.NEXT_ITEM -> SwipeableSize.Medium
        CardListPosition.POST_NEXT -> SwipeableSize.Small
        CardListPosition.INVISIBLE -> SwipeableSize.Invisible
    }
    LayoutSwipeableCard(
        swipeableState = swipeableState,
        swipeEnabled = swipeEnabled,
        cardContent = {
            Card(
                shape = RoundedCornerShape(corner = CornerSize(32.dp)),
                modifier = modifier
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    if (wordOfDayModel.image.url.isNotBlank()) {
                        AsyncImage(
                            model = wordOfDayModel.image.url,
                            contentDescription = stringResource(id = R.string.word_of_day_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = BrushPainter(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0x2C, 0xDD, 0x79),
                                        Color(0x11, 0xFF, 0xA9)
                                    )
                                )
                            ),
                            contentDescription = stringResource(id = R.string.word_of_day_image),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight(0.5f)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = wordOfDayModel.word,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth(0.72f)
                                    .padding(bottom = 24.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                                        color = Color(
                                            red = 0xFF,
                                            green = 0xFF,
                                            blue = 0xFF,
                                            alpha = 0x80
                                        )
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.pub_dic_icon),
                                        contentDescription = stringResource(id = R.string.pub_dict),
                                        tint = Color.White,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.pub_dict),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                                    )
                                }
                            }
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                                    .background(
                                        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                                        color = Color(
                                            red = 0xFF,
                                            green = 0xFF,
                                            blue = 0xFF,
                                            alpha = 0x80
                                        )
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.bookmark_icon),
                                    contentDescription = stringResource(id = R.string.bookmark),
                                    tint = Color.White,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                                        color = Color(
                                            red = 0xFF,
                                            green = 0xFF,
                                            blue = 0xFF,
                                            alpha = 0x80
                                        )
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.share_icon),
                                    contentDescription = stringResource(id = R.string.share),
                                    tint = Color.White,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        cardSize = cardSize,
        onSwiped = onSwiped
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LayoutSwipeableCard(
    swipeableState: SwipeableCardState,
    cardContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true,
    cardSize: SwipeableSize,
    onSwiped: (target: SwipeCardAnchor) -> Unit
) {
    var process by remember { mutableFloatStateOf(0.1f) }
    process = when (swipeableState.anchoredDraggableState.progress) {
        1.0f -> 0.0f
        0.99f -> 1.0f
        else -> swipeableState.anchoredDraggableState.progress
    }
    val initialValue = if (cardSize == SwipeableSize.Invisible) SwipeableSize.Small else cardSize
    val targetValue = when (cardSize) {
        SwipeableSize.Invisible -> SwipeableSize.Small
        SwipeableSize.Small -> SwipeableSize.Medium
        SwipeableSize.Medium -> SwipeableSize.Default
        SwipeableSize.Default -> SwipeableSize.Default
    }
    var currentValue by remember { mutableStateOf(initialValue.height) }
    currentValue =
        if (initialValue == SwipeableSize.Small || initialValue == SwipeableSize.Medium)
            initialValue.height.plus(targetValue.height.minus(initialValue.height).times(process))
        else
            initialValue.height

    var lastAnchor by remember { mutableStateOf(swipeableState.initialValue) }
    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue != lastAnchor) {
            onSwiped(swipeableState.currentValue)
        }
        lastAnchor = swipeableState.currentValue
    }
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val widthPx = with(density) { screenWidthDp.toPx() }
    swipeableState.anchoredDraggableState.updateAnchors (
        DraggableAnchors {
            SwipeCardAnchor.RightEnd at -widthPx
            SwipeCardAnchor.Start at 0f
            SwipeCardAnchor.LeftEnd at widthPx
        },
        newTarget = swipeableState.currentValue
    )
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .alpha(if (cardSize == SwipeableSize.Invisible) 0f else 1f)
                    .layoutId(SWIPEABLE_CARD_LAYOUT)
                    .size(currentValue)
                    .offset {
                        IntOffset(
                            x = swipeableState
                                .anchoredDraggableState
                                .requireOffset()
                                .roundToInt(),
                            y = 0
                        )
                    }
                    .anchoredDraggable(
                        state = swipeableState.anchoredDraggableState,
                        enabled = swipeEnabled,
                        orientation = Orientation.Horizontal,
                        startDragImmediately = true
                    )
            ) {
                cardContent()
            }
                  },
        measurePolicy =  swipeableMeasureWithoutSize()
    )
}

@Composable
internal fun swipeableMeasureWithoutSize():
        MeasureScope.(measurables: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val card = measurables.first{ it.layoutId == SWIPEABLE_CARD_LAYOUT }
            .measure(constraints)
        val layoutHeight = card.height
        layout(width = constraints.maxWidth, height = layoutHeight) {
            card.let { placeable ->
                val xPosition = (constraints.maxWidth - placeable.width) / 2
                placeable.place(x = xPosition, 0)
            }
        }
    }
}

@Composable
internal fun swipeableMeasure(
    size: SwipeableSize
): MeasureScope.(measurables: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val card = measurables.first{ it.layoutId == SWIPEABLE_CARD_LAYOUT }.measure(
            constraints = constraints.copy(
                maxHeight = size.height.toPx().roundToInt(),
                maxWidth = (constraints.maxWidth * size.widthAlpha).roundToInt()
            )
        )
        val text = measurables.first { it.layoutId == TEXT_LAYOUT }
            .measure(constraints)
        val layoutHeight = size.height.toPx().roundToInt()
        layout(width = constraints.maxWidth, height = layoutHeight) {
            card.let { placeable ->
                val xPosition = (constraints.maxWidth - placeable.width) / 2
                placeable.place(x = xPosition, 0)
            }
            text.let { placeable ->
                val xPosition = (constraints.maxWidth - placeable.width) / 2
                placeable.place(x = xPosition, 1000)
            }
        }
    }
}

private const val SWIPEABLE_CARD_LAYOUT = "SWIPEABLE_CARD_LAYOUT"

private const val TEXT_LAYOUT = "TEXT_LAYOUT"

enum class CardListPosition {
    CURRENT_ITEM,
    NEXT_ITEM,
    POST_NEXT,
    INVISIBLE
}

enum class SwipeableSize(
    val widthAlpha: Float,
    val height: Dp
) {
    Invisible(widthAlpha = 0f, height = 0.dp),
    Small(widthAlpha = 0.72f, height = 290.dp),
    Medium(widthAlpha = 0.85f, height = 340.dp),
    Default(widthAlpha = 1f, height = 400.dp)
}

@Preview(showBackground = true)
@Composable
private fun LayoutWordOfDayPicturePagerPreview() {
    WordsOfDayCardPager(wordsOfDay = wordsOfDay)
}

@Preview(showBackground = true)
@Composable
private fun LayoutWordOfDayPreview() {
    ExpWordOfDayCard(
        wordOfDayModel = wordsOfDay[0],
        cardListPosition = CardListPosition.CURRENT_ITEM,
    ) { }
}