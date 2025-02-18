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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.dictionary.LanguageModel
import kotlin.math.roundToInt

@Composable
fun LanguageConfigurationTopBar(
    translationLangList: List<LanguageModel>,
    language: LanguageModel,
    onUpdateTranLanguage: (LanguageModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val tranLangState = remember { mutableStateOf(TranslationLanguageState.Default) }
    var visible by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        PhrasebookLanguages(
            language = language,
            tranLangState = tranLangState,
            languageListVisible = visible,
            translationLangList = translationLangList,
            onTranslationLanguageClick = {
                tranLangState.value =
                    if (tranLangState.value == TranslationLanguageState.Default) TranslationLanguageState.Select
                    else TranslationLanguageState.Default
                visible = !visible
            },
            onTranslationLanguageItemClick = { translationLanguage ->
                onUpdateTranLanguage(translationLanguage)
                tranLangState.value =
                    if (tranLangState.value == TranslationLanguageState.Default) TranslationLanguageState.Select
                    else TranslationLanguageState.Default
                visible = !visible
            }
        )
    }
}

@Composable
fun PhrasebookLanguages(
    language: LanguageModel,
    tranLangState: State<TranslationLanguageState>,
    onTranslationLanguageClick: () -> Unit,
    languageListVisible: Boolean,
    translationLangList: List<LanguageModel>,
    onTranslationLanguageItemClick: (LanguageModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LayoutPhrasebookLanguages(
        originLang = {
            OriginLanguage(originLang = com.example.presentation_common.R.string.phrasebook_origin_lang)
        },
        iconArrow = {
            ArrowIcon(resource = R.drawable.transition_icon, R.string.transiton_test)
        },
        tranLanguage = {
            TranslationLanguage(
                language = language,
                tranLangState = tranLangState
            )
        },
        tranLanguages = {
            AnimatedVisibility(
                visible = languageListVisible,
                enter = fadeIn(tween(250)),
                exit = fadeOut(tween(250))
            ) {
                TranslationLanguageList(
                    translationLangList = translationLangList,
                    onTranslationLanguageItemClick = onTranslationLanguageItemClick,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        },
        onTranslationLanguageClick = onTranslationLanguageClick,
        modifier = modifier
    )
}

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
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = originLang),
            style = MaterialTheme.typography.bodyLarge
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
            .fillMaxSize()
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
    modifier: Modifier = Modifier
) {
    val rotation = remember {
        Animatable(
            initialValue = 0,
            typeConverter = Int.VectorConverter
        )
    }
    LaunchedEffect(Unit) {
        snapshotFlow { tranLangState.value }
            .collect {
                rotation.animateTo(
                    targetValue = if (it == TranslationLanguageState.Select) 180 else 0,
                    animationSpec = tween(300)
                )
            }
    }
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(corner = CornerSize(24.dp)),
                    color = Color(0x99FFFFFF)
                )
                .fillMaxSize()
        ) {
            Text(
                text = language.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 16.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
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
}

@Composable
fun LayoutPhrasebookLanguages(
    originLang: @Composable () -> Unit,
    iconArrow: @Composable () -> Unit,
    tranLanguage: @Composable () -> Unit,
    tranLanguages: @Composable () -> Unit,
    onTranslationLanguageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId(TopBarLayout.ORIGIN_LANGUAGE)
            ) {
                originLang()
            }
            Box(modifier = Modifier.layoutId(TopBarLayout.ICON_ARROW)) {
                iconArrow()
            }
            Box(modifier = Modifier
                .layoutId(TopBarLayout.TRANSLATION_LANGUAGE)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onTranslationLanguageClick() }
                )
            ) {
                tranLanguage()
            }
            Box(
                modifier = Modifier
                    .layoutId(TopBarLayout.TRANSLATION_LANGUAGES)
            ) {
                tranLanguages()
            }
        },
        measurePolicy = phrasebookLanguagesTopBarMeasure()
    )
}

@Composable
fun phrasebookLanguagesTopBarMeasure(): MeasureScope.(measurable: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurable, constraints ->
        val langWidth = LanguageSize.Medium.width.toPx().roundToInt()
        val langHeight = LanguageSize.Medium.height.toPx().roundToInt()
        val originLang = measurable.first { it.layoutId == TopBarLayout.ORIGIN_LANGUAGE }
            .measure(
                constraints.copy(
                    maxHeight = langHeight,
                    maxWidth = langWidth
                )
            )
        val iconArrow = measurable.first { it.layoutId == TopBarLayout.ICON_ARROW }
            .measure(
                constraints.copy(
                    maxWidth = langHeight,
                    maxHeight = langHeight
                )
            )
        val translationLang = measurable.first { it.layoutId == TopBarLayout.TRANSLATION_LANGUAGE }
            .measure(
                constraints.copy(
                    maxHeight = langHeight,
                    maxWidth = langWidth
                )
            )
        val translationLangs = measurable.first { it.layoutId == TopBarLayout.TRANSLATION_LANGUAGES }
            .measure(constraints)

        val layoutWidth = constraints.maxWidth
        val layoutHeight = translationLang.height + translationLangs.height
        layout(layoutWidth, layoutHeight) {
            val iconArrowX = (layoutWidth - iconArrow.width) / 2
            val originLangX = (layoutWidth / 2 - iconArrow.width) - originLang.width
            val translationLangX = (layoutWidth / 2 + iconArrow.width)
            originLang.place(x = originLangX, y = 0)
            iconArrow.place(x = iconArrowX, y = 0)
            translationLang.place(x = translationLangX, y = 0)
            translationLangs.place(x = translationLangX, y = langHeight)
        }
    }
}

enum class TopBarLayout {
    ORIGIN_LANGUAGE, ICON_ARROW, TRANSLATION_LANGUAGE, TRANSLATION_LANGUAGES
}

enum class TranslationLanguageState {
    Default, Select
}

enum class LanguageSize(val width: Dp, val height: Dp) {
    Medium(width = 140.dp, height = 48.dp)
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun PhrasebookLanguagesPreview() {
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
        ) {
            PhrasebookLanguages(
                LanguageModel(1, "Русский", "rus"),
                tranLangState = remember { mutableStateOf(TranslationLanguageState.Default) },
                onTranslationLanguageClick = {},
                onTranslationLanguageItemClick = {},
                translationLangList = listOf(LanguageModel(1, "Русский", "rus")),
                languageListVisible = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarLayoutPreviewNew() {
    val lang = LanguageModel(1, "Русский", "rus")
    val tranLangList = listOf(
        LanguageModel(1, "Русский", "rus"),
        LanguageModel(1, "Турецкий", "rus"),
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
            LanguageConfigurationTopBar(
                translationLangList = tranLangList,
                language = lang,
                onUpdateTranLanguage = {}
            )
        }
    }
}