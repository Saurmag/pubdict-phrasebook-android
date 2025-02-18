@file:JvmName("CardPagerKt")

package com.example.presentation_dictionary.design.swipeablecard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun <T : Any> CycleCardPager(
    items: List<T>,
    onCardClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit,
) {
    var frontItemIndex by remember { mutableIntStateOf(0) }
    var itemsListState by remember { mutableStateOf(items) }
    val scope = rememberCoroutineScope()
    val lastCardAnimatableWidth =
        remember { Animatable(initialValue = CardPagerLayout.LastCard.width) }
    val lastCardAnimatableHeight =
        remember { Animatable(initialValue = CardPagerLayout.LastCard.height) }
    val middleCardAnimatableWidth =
        remember { Animatable(initialValue = CardPagerLayout.MiddleCard.width) }
    val middleCardAnimatableHeight =
        remember { Animatable(initialValue = CardPagerLayout.MiddleCard.height) }
    val cards = @Composable {
        for (cardIndex in frontItemIndex..itemsListState.lastIndex) {
            key(cardIndex) {
                val cardType = when (cardIndex) {
                    frontItemIndex -> CardPagerLayout.FrontCard
                    frontItemIndex + 1 -> CardPagerLayout.MiddleCard
                    frontItemIndex + 2 -> CardPagerLayout.LastCard
                    else -> CardPagerLayout.NonVisible
                }
                val cardWidth = when (cardType) {
                    CardPagerLayout.MiddleCard -> {
                        middleCardAnimatableWidth.value
                    }

                    CardPagerLayout.LastCard -> {
                        lastCardAnimatableWidth.value
                    }

                    else -> {
                        cardType.width
                    }
                }
                val cardHeight = when (cardType) {
                    CardPagerLayout.MiddleCard -> {
                        middleCardAnimatableHeight.value
                    }

                    CardPagerLayout.LastCard -> {
                        lastCardAnimatableHeight.value
                    }

                    else -> {
                        cardType.height
                    }
                }
                Box(
                    modifier = Modifier
                        .layoutId(cardType)
                        .size(
                            width = cardWidth.dp,
                            height = cardHeight.dp
                        )
                        .swipeToChange(
                            cardType = cardType,
                            onCardClick = { onCardClick(itemsListState[cardIndex]) },
                            onSwiped = {
                                scope.launch {
                                    val toFrontSizeJob = scope
                                        .launch {
                                            launch {
                                                middleCardAnimatableWidth.animateTo(
                                                    targetValue = CardPagerLayout.MiddleCard.targetWidth,
                                                    animationSpec = tween(100)
                                                )
                                            }
                                            launch {
                                                middleCardAnimatableHeight.animateTo(
                                                    targetValue = CardPagerLayout.MiddleCard.targetHeight,
                                                    animationSpec = tween(100)
                                                )
                                            }
                                        }
                                        .also { it.join() }
                                    launch {
                                        lastCardAnimatableWidth.animateTo(
                                            targetValue = CardPagerLayout.LastCard.targetWidth,
                                            animationSpec = tween(100)
                                        )
                                    }
                                    launch {
                                        lastCardAnimatableHeight.animateTo(
                                            targetValue = CardPagerLayout.LastCard.targetHeight,
                                            animationSpec = tween(100)
                                        )
                                    }
                                    if (toFrontSizeJob.isCompleted) {
                                        launch {
                                            lastCardAnimatableWidth.snapTo(
                                                targetValue = CardPagerLayout.LastCard.width,
                                            )
                                        }
                                        launch {
                                            lastCardAnimatableHeight.snapTo(
                                                targetValue = CardPagerLayout.LastCard.height,
                                            )
                                        }
                                        launch {
                                            middleCardAnimatableWidth.snapTo(
                                                targetValue = CardPagerLayout.MiddleCard.width,
                                            )
                                        }
                                        launch {
                                            middleCardAnimatableHeight.snapTo(
                                                targetValue = CardPagerLayout.MiddleCard.height,
                                            )
                                        }
                                    }
                                    val currentList = itemsListState
                                    val otherItems = currentList.subList(0, itemsListState.size)
                                    val frontItem = itemsListState[cardIndex]
                                    itemsListState = otherItems + frontItem
                                    frontItemIndex += 1
                                }
                            }
                        )
                ) {
                    itemContent(itemsListState[cardIndex])
                }
            }
        }
    }
    SubcomposeLayout { constraints ->
        val cardsPlaceable = subcompose(CARD_PAGER, cards)
        val frontCardPlaceable = cardsPlaceable
            .first { it.layoutId == CardPagerLayout.FrontCard }
            .measure(
                Constraints(
                    maxWidth = constraints.maxWidth,
                    maxHeight = constraints.maxHeight
                )
            )
        val middleCardPlaceable = cardsPlaceable
            .first { it.layoutId == CardPagerLayout.MiddleCard }
            .measure(
                Constraints(
                    maxWidth = constraints.maxWidth,
                    maxHeight = constraints.maxHeight
                )
            )
        val lastCardPlaceable = cardsPlaceable
            .first { it.layoutId == CardPagerLayout.LastCard }
            .measure(
                Constraints(
                    maxWidth = constraints.maxWidth,
                    maxHeight = constraints.maxHeight
                )
            )
        val lazyCardPlaceables = cardsPlaceable
            .filter { it.layoutId == CardPagerLayout.LazyCard }
            .map {
                it.measure(
                    Constraints(
                        maxWidth = constraints.maxWidth,
                        maxHeight = constraints.maxHeight
                    )
                )
            }
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        layout(
            width = layoutWidth,
            height = layoutHeight,
        ) {
            val yFrontPosition = layoutHeight / 2 - frontCardPlaceable.height / 2
            val yMiddlePosition = yFrontPosition - 16.dp.roundToPx()
            val yPostNextPosition = yMiddlePosition - 16.dp.roundToPx()
            lazyCardPlaceables.forEach {
                it.placeRelative(
                    x = (constraints.maxWidth - lastCardPlaceable.width) / 2,
                    y = yPostNextPosition
                )
            }
            lastCardPlaceable.placeRelative(
                x = (constraints.maxWidth - lastCardPlaceable.width) / 2,
                y = yPostNextPosition
            )
            middleCardPlaceable.placeRelative(
                x = (constraints.maxWidth - middleCardPlaceable.width) / 2,
                y = yMiddlePosition
            )
            frontCardPlaceable.placeRelative(
                x = (constraints.maxWidth - frontCardPlaceable.width) / 2,
                y = yFrontPosition
            )
        }
    }
}

enum class CardPagerLayout(
    val width: Float,
    val height: Float,
    val targetWidth: Float,
    val targetHeight: Float
) {
    FrontCard(
        width = 360f,
        height = 400f,
        targetWidth = 360f,
        targetHeight = 400f
    ),
    MiddleCard(
        width = 315f,
        height = 340f,
        targetWidth = 360f,
        targetHeight = 400f
    ),
    LastCard(
        width = 265f,
        height = 290f,
        targetWidth = 315f,
        targetHeight = 340f
    ),
    LazyCard(
        width = 265f,
        height = 290f,
        targetWidth = 265f,
        targetHeight = 290f
    ),
    NonVisible(
        width = 265f,
        height = 290f,
        targetWidth = 265f,
        targetHeight = 290f
    )
}

private const val CARD_PAGER = "CARDS"


fun Modifier.swipeToChange(
    cardType: CardPagerLayout,
    onCardClick: () -> Unit,
    onSwiped: () -> Unit
): Modifier = composed {
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val widthPx = with(density) { screenWidthDp.toPx() }

    var tapEnabled by remember { mutableStateOf(true) }

    val animatableSaver = Saver<Animatable<Float, AnimationVector1D>, Float>(
        save = { it.value },
        restore = { Animatable(it) }
    )
    val animatableOffsetX = rememberSaveable(saver = animatableSaver) { Animatable(0f) }
    val animatableRotate = rememberSaveable(saver = animatableSaver) { Animatable(0f) }
    animatableRotate.updateBounds(
        lowerBound = -20f,
        upperBound = 20f
    )
    offset { IntOffset(x = animatableOffsetX.value.roundToInt(), y = 0) }
        .graphicsLayer { rotationZ = animatableRotate.value }
        .pointerInput(cardType == CardPagerLayout.FrontCard && tapEnabled) {
            awaitEachGesture {
                awaitFirstDown(pass = PointerEventPass.Final)
                val up = waitForUpOrCancellation()
                if (up != null && tapEnabled) {
                    up.consume()
                    onCardClick()
                }
            }
        }
        .pointerInput(cardType == CardPagerLayout.FrontCard) {
            if (cardType == CardPagerLayout.FrontCard) {
                val decay = splineBasedDecay<Float>(this)
                coroutineScope {
                    while (true) {
                        val velocityTracker = VelocityTracker()
                        animatableOffsetX.stop()
                        awaitPointerEventScope {
                            val pointerId = awaitFirstDown(
                                requireUnconsumed = false,
                                pass = PointerEventPass.Initial
                            ).id
                            horizontalDrag(pointerId = pointerId) { change ->
                                if (change.pressed) {
                                    tapEnabled = false
                                }
                                launch {
                                    animatableOffsetX.snapTo(
                                        targetValue = animatableOffsetX.value + change.positionChange().x
                                    )
                                    animatableRotate.snapTo(
                                        targetValue = animatableRotate.value + change.positionChange().x / 50
                                    )
                                }
                                velocityTracker.addPosition(
                                    change.uptimeMillis,
                                    change.position
                                )
                            }
                        }
                        val velocity = cardNormalizeVelocity(
                            velocity = velocityTracker.calculateVelocity().x,
                            positionX = animatableOffsetX.value
                        )
                        val targetOffsetX = decay.calculateTargetValue(
                            initialValue = animatableOffsetX.value,
                            initialVelocity = velocity,
                        )
                        animatableOffsetX.updateBounds(
                            lowerBound = -widthPx,
                            upperBound = widthPx
                        )
                        when {
                            targetOffsetX.absoluteValue <= widthPx * 0.3 -> {
                                tapEnabled = true
                                launch {
                                    animatableOffsetX.animateTo(
                                        targetValue = 0f,
                                        initialVelocity = velocity,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                                launch {
                                    animatableRotate.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                            }

                            targetOffsetX.absoluteValue <= -widthPx / 2 * 0.3 -> {
                                tapEnabled = true
                                launch {
                                    animatableOffsetX.animateTo(
                                        targetValue = 0f,
                                        initialVelocity = velocity,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                                launch {
                                    animatableRotate.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                            }

                            else -> {
                                if (velocity in -2000f..2000f) {
                                    val targetValueOffsetX =
                                        if (animatableOffsetX.value < 0) -widthPx else widthPx
                                    val offsetJob = launch {
                                        animatableOffsetX.animateTo(
                                            targetValue = targetValueOffsetX,
                                            animationSpec = tween(durationMillis = 200)
                                        )

                                    }.also { it.join() }
                                    val rotateJob = launch {
                                        animatableRotate.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(durationMillis = 200)
                                        )
                                    }.also { it.join() }
                                    if (rotateJob.isCompleted && offsetJob.isCompleted) {
                                        onSwiped()
                                    }
                                } else {
                                    val decayAnimationJob = launch {
                                        animatableOffsetX.animateDecay(
                                            initialVelocity = velocity,
                                            animationSpec = decay
                                        )
                                    }.also { it.join() }
                                    if (decayAnimationJob.isCompleted) {
                                        onSwiped()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}

fun cardNormalizeVelocity(velocity: Float, positionX: Float): Float {
    val isRightSwipe = positionX > 0
    val normalizeVelocity: Float
    if (isRightSwipe) {
        normalizeVelocity = when (if (velocity < 0) velocity * -1 else velocity) {
            in 0f..2000f -> 0f
            in 2001f..5000f -> 3500f
            in 5001f..10000f -> 5500f
            in 10001f..15000f -> 7500f
            in 15001f..20000f -> 9500f
            else -> 11500f
        }
    } else {
        normalizeVelocity = when (if (velocity > 0) velocity * -1 else velocity) {
            in -2000f..0f -> 0f
            in -5000f..-2001f -> -3500f
            in -10000f..-5001f -> -5500f
            in -15000f..-10001f -> -7500f
            in -20000f..-15001f -> -9500f
            else -> -11500f
        }
    }
    return normalizeVelocity
}

@Preview(showBackground = true)
@Composable
fun NewCardPagerPreview() {
    val colorList =
        listOf(
            ColorPreview(0, Color.Black),
            ColorPreview(1, Color.Green),
            ColorPreview(2, Color.Red),
            ColorPreview(2, Color.Magenta)
        )
    Column(modifier = Modifier.fillMaxSize()) {
        CycleCardPager(
            items = colorList,
            itemContent = { color ->
                Box(
                    modifier = Modifier
                        .width(1000.dp)
                        .height(400.dp)
                        .background(color = color.color, shape = RoundedCornerShape(8.dp))
                )
            },
        )
    }
}

private data class ColorPreview(
    val id: Int,
    val color: Color
)