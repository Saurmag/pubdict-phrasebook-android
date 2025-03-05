package com.example.presentation_dictionary.design.language_top_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.dictionary.LanguageModel
import kotlinx.coroutines.launch

@Composable
fun LanguageTopBar(
    translationLangList: List<LanguageModel>,
    language: LanguageModel,
    onUpdateTranLanguage: (LanguageModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val offsetPx = with(density) { 40.dp.roundToPx() }
    val tranLangState = remember { mutableStateOf(TranslationLanguageState.Default) }
    val visible = remember { mutableStateOf(false) }
    val barOffsetX = remember { Animatable(0, Int.VectorConverter) }
    val originWidth = remember { Animatable(LanguageSize.Medium.width, Dp.VectorConverter) }
    val scope = rememberCoroutineScope()

    LanguageTopBarLayout(
        originLang = {
            OriginLanguage(
                originLang = com.example.presentation_common.R.string.phrasebook_origin_lang,
                modifier = Modifier.width(originWidth.value)
            )
        },
        arrowIcon = {
            ArrowIcon(
                resource = R.drawable.transition_icon, R.string.transiton_test,
                modifier = Modifier
            )
        },
        translationLang = {
            TranslationLanguage(
                language = language,
                tranLangState = tranLangState,
                onTranslationLanguageClick = {
                    tranLangState.value =
                        if (tranLangState.value == TranslationLanguageState.Default) {
                            scope.launch {
                                barOffsetX.animateTo(
                                    targetValue = -offsetPx,
                                    tween(300)
                                )
                            }
                            scope.launch {
                                originWidth.animateTo(
                                    targetValue = LanguageSize.MediumReduced.width,
                                    tween(300)
                                )
                            }
                            TranslationLanguageState.Select
                        } else {
                            scope.launch {
                                barOffsetX.animateTo(
                                    targetValue = 0,
                                    tween(300)
                                )
                            }
                            scope.launch {
                                originWidth.animateTo(
                                    targetValue = LanguageSize.Medium.width,
                                    tween(300)
                                )
                            }
                            TranslationLanguageState.Default
                        }
                    visible.value = !visible.value
                },
            )
        },
        langSelection = {
            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(tween(250)),
                exit = fadeOut(tween(250))
            ) {
                TranslationLanguageList(
                    translationLangList = translationLangList,
                    onTranslationLanguageItemClick = { translationLanguage ->
                        onUpdateTranLanguage(translationLanguage)
                        tranLangState.value =
                            if (tranLangState.value == TranslationLanguageState.Default) {
                                scope.launch {
                                    barOffsetX.animateTo(
                                        targetValue = -offsetPx,
                                        tween(300)
                                    )
                                }
                                scope.launch {
                                    originWidth.animateTo(
                                        targetValue = LanguageSize.MediumReduced.width,
                                        tween(300)
                                    )
                                }
                                TranslationLanguageState.Select
                            }
                            else {
                                scope.launch {
                                    barOffsetX.animateTo(
                                        targetValue = 0,
                                        tween(300)
                                    )
                                }
                                scope.launch {
                                    originWidth.animateTo(
                                        targetValue = LanguageSize.Medium.width,
                                        tween(300)
                                    )
                                }
                                TranslationLanguageState.Default
                            }
                        visible.value = !visible.value
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        },
        modifier = modifier.offset { IntOffset(x = barOffsetX.value, y = 0) }
    )
}

@Composable
fun LanguageTopBarLayout(
    originLang: @Composable () -> Unit,
    arrowIcon: @Composable () -> Unit,
    translationLang: @Composable () -> Unit,
    langSelection: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        content = {
            Box(
                modifier = Modifier.layoutId(LanguageTopBarContent.OriginLang)
            ) {
                originLang()
            }
            Box(
                modifier = Modifier.layoutId(LanguageTopBarContent.ArrowIcon)
            ) {
                arrowIcon()
            }
            Box(
                modifier = Modifier.layoutId(LanguageTopBarContent.TranslationLang)
            ) {
                translationLang()
            }
            Box(
                modifier = Modifier.layoutId(LanguageTopBarContent.SelectionLang)
            ) {
                langSelection()
            }
        },
        measurePolicy = measureLanguageTopBarLayout(),
        modifier = modifier
    )
}

private fun measureLanguageTopBarLayout():
        MeasureScope.(List<Measurable>, Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val maxLangHeight = LanguageSize.Medium.height.roundToPx()
        val maxOriginLangWidth = LanguageSize.Medium.width.roundToPx()
        val maxTranslationLangWidth = LanguageSize.MediumExpanded.width.roundToPx()

        val originLang = measurables.first { it.layoutId == LanguageTopBarContent.OriginLang }
            .measure(Constraints(maxHeight = maxLangHeight, maxWidth = maxOriginLangWidth))

        val arrowIcon = measurables.first { it.layoutId == LanguageTopBarContent.ArrowIcon }
            .measure(Constraints(maxHeight = maxLangHeight, maxWidth = maxLangHeight))

        val translationLang =
            measurables.first { it.layoutId == LanguageTopBarContent.TranslationLang }
                .measure(Constraints(maxHeight = maxLangHeight, maxWidth = maxTranslationLangWidth))

        val selectionLang = measurables.first { it.layoutId == LanguageTopBarContent.SelectionLang }
            .measure(Constraints())

        val layoutWidth = constraints.maxWidth
        layout(width = layoutWidth, height = maxLangHeight) {
            val iconArrowX = (layoutWidth - arrowIcon.width) / 2
            val originLangX = (layoutWidth / 2 - 32.dp.roundToPx()) - originLang.width
            val translationLangX = (layoutWidth / 2 + 32.dp.roundToPx())
            originLang.place(x = originLangX, y = 0)
            arrowIcon.place(x = iconArrowX, y = 0)
            translationLang.place(x = translationLangX, y = 0)
            selectionLang.place(x = translationLangX, y = maxLangHeight)
        }
    }
}

enum class LanguageTopBarContent { OriginLang, ArrowIcon, TranslationLang, SelectionLang }

@Composable
fun OriginLanguage(
    @StringRes originLang: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                shape = RoundedCornerShape(corner = CornerSize(24.dp)),
                color = Color(0x99FFFFFF)
            )
    ) {
        Text(
            text = stringResource(id = originLang),
            style = MaterialTheme.typography.bodyLarge,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
        )
    }
}

@Composable
fun ArrowIcon(
    @DrawableRes resource: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(48.dp))
            .background(color = Color.White)
            .size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = description),
        )
    }
}

@Composable
fun TranslationLanguage(
    language: LanguageModel,
    tranLangState: State<TranslationLanguageState>,
    onTranslationLanguageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation = remember {
        Animatable(
            initialValue = 0,
            typeConverter = Int.VectorConverter
        )
    }
    val tranLangWidth = remember {
        Animatable(initialValue = LanguageSize.Medium.width, typeConverter = Dp.VectorConverter)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        snapshotFlow { tranLangState.value }
            .collect {
                scope.launch {
                    tranLangWidth.animateTo(
                        targetValue = if (it == TranslationLanguageState.Select) LanguageSize.MediumExpanded.width else LanguageSize.Medium.width,
                        animationSpec = tween(300)
                    )
                }
                scope.launch {
                    rotation.animateTo(
                        targetValue = if (it == TranslationLanguageState.Select) 180 else 0,
                        animationSpec = tween(300)
                    )
                }
            }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .width(tranLangWidth.value)
            .clip(shape = RoundedCornerShape(corner = CornerSize(24.dp)))
            .background(
                color = Color(0x99FFFFFF)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                ),
                role = Role.Button
            ) { onTranslationLanguageClick() }
    ) {
        Text(
            text = language.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                .background(
                    shape = RoundedCornerShape(corner = CornerSize(24.dp)),
                    color = Color.Black
                )
                .size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_icon),
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer { rotationZ = rotation.value.toFloat() }
            )
        }
    }

}

enum class TranslationLanguageState {
    Default, Select
}

enum class LanguageSize(val width: Dp, val height: Dp) {
    MediumReduced(width = 100.dp, height = 48.dp),
    Medium(width = 140.dp, height = 48.dp),
    MediumExpanded(width = 180.dp, height = 48.dp)
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val lang = LanguageModel(1, "Русский", "rus")
    val tranLangList = listOf(
        LanguageModel(1, "Русский", "rus"),
        LanguageModel(1, "Табасаранский", "rus"),
        LanguageModel(1, "Английский", "rus")
    )
    PubDictTheme {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0x2C, 0xDD, 0x79),
                            Color(0x11, 0xFF, 0xA9)
                        )
                    )
                )
                .fillMaxSize()
        ) {
            LanguageTopBar(
                translationLangList = tranLangList,
                language = lang,
                onUpdateTranLanguage = {}
            )
        }
    }
}
