package com.example.presentation_word_of_day.swipeablecard

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable

@OptIn(ExperimentalFoundationApi::class)
internal class SwipeableCardState(
    val initialValue: SwipeCardAnchor,
    positionalThreshold: (totalDistance: Float) -> Float,
    snapAnimationSpec: AnimationSpec<Float>,
    private val velocityThreshold: () -> Float,
    confirmValueChange: (newValue: SwipeCardAnchor) -> Boolean = { true }
) {
    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        anchors = DraggableAnchors {
            SwipeCardAnchor.LeftEnd at Float.MIN_VALUE
            SwipeCardAnchor.Start at 0f
            SwipeCardAnchor.RightEnd at Float.MAX_VALUE
        },
        positionalThreshold = positionalThreshold,
        velocityThreshold = velocityThreshold,
        animationSpec = snapAnimationSpec,
        confirmValueChange = confirmValueChange
    )

    val currentValue: SwipeCardAnchor by derivedStateOf {
        anchoredDraggableState.currentValue
    }

    val targetValue: SwipeCardAnchor by derivedStateOf {
        anchoredDraggableState.targetValue
    }
    companion object {
        fun Saver(
            snapAnimationSpec: AnimationSpec<Float>,
            positionalThreshold: (distance: Float) -> Float,
            velocityThreshold: () -> Float,
            confirmValueChange: (SwipeCardAnchor) -> Boolean = { true },
        ) = androidx.compose.runtime.saveable.Saver<SwipeableCardState, SwipeCardAnchor>(
            save = { it.currentValue },
            restore = {
                SwipeableCardState(
                    initialValue = it,
                    snapAnimationSpec = snapAnimationSpec,
                    confirmValueChange = confirmValueChange,
                    positionalThreshold = positionalThreshold,
                    velocityThreshold = velocityThreshold,
                )
            }
        )
    }
}

enum class SwipeCardAnchor { Start, RightEnd, LeftEnd }

@Composable
internal fun rememberSwipeableButtonState(
    initialValue: SwipeCardAnchor,
    velocityThreshold: () -> Float,
    positionalThreshold: (totalDistance: Float) -> Float = { distance -> distance * 0.5f },
    snapAnimationSpec: AnimationSpec<Float> = tween(),
    confirmValueChange: (newValue: SwipeCardAnchor) -> Boolean = { true },
): SwipeableCardState {
    return rememberSaveable(
        saver = SwipeableCardState.Saver(
            snapAnimationSpec = snapAnimationSpec,
            positionalThreshold = positionalThreshold,
            velocityThreshold = velocityThreshold,
            confirmValueChange = confirmValueChange,
        )
    ) {
        SwipeableCardState(
            initialValue = initialValue,
            positionalThreshold = positionalThreshold,
            velocityThreshold = velocityThreshold,
            snapAnimationSpec = snapAnimationSpec,
            confirmValueChange = confirmValueChange
        )
    }
}