package com.example.presentation_word_of_day.language_top_bar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.entity.phrasebook.Language
import com.example.presentation_phrasebook.R
import kotlin.math.roundToInt

@Composable
fun PhrasebookLanguages(
    language: Language,
    modifier: Modifier = Modifier
) {
    var arrowState by remember { mutableStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = arrowState,
        animationSpec = tween(300), label = ""
    )
    LayoutPhrasebookLanguages(
        originLang = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(corner = CornerSize(24.dp)),
                        color = Color(
                            red = 0xFF,
                            green = 0xFF,
                            blue = 0xFF,
                            alpha = 0x99
                        )
                    )
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.phrasebook_origin_lang),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        iconArrow = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(48.dp))
                    .background(color = Color.White)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.transition_icon),
                    contentDescription = stringResource(id = R.string.transiton_test),
                )
            }
        },
        translationLang = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(corner = CornerSize(24.dp)),
                        color = Color(
                            red = 0xFF,
                            green = 0xFF,
                            blue = 0xFF,
                            alpha = 0x99
                        )
                    )
                    .fillMaxSize()
            ) {
                Text(
                    text = language.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
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
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
        },
        onTranslationLanguageClick = {
            arrowState = if (arrowState == 0f) 180f else 0f
        }
    )
}

@Composable
fun LayoutPhrasebookLanguages(
    originLang: @Composable () -> Unit,
    iconArrow: @Composable () -> Unit,
    translationLang: @Composable () -> Unit,
    onTranslationLanguageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        content = {
            Box(modifier = Modifier
                .layoutId(TopBarLayout.ORIGIN_LANGUAGE)
                .padding(end = 12.dp)) {
                originLang()
            }
            Box(modifier = Modifier.layoutId(TopBarLayout.ICON_ARROW)) {
                iconArrow()
            }
            Box(modifier = Modifier
                .layoutId(TopBarLayout.TRANSLATION_LANGUAGE)
                .padding(start = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onTranslationLanguageClick() }
                )
            ) {
                translationLang()
            }
        },
        measurePolicy = phrasebookLanguagesTopBarMeasure()
    )
}

@Composable
fun phrasebookLanguagesTopBarMeasure(): MeasureScope.(measurable: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurable, constraints ->
        val langWidth = 140.dp.toPx().roundToInt()
        val maxHeight = 48.dp.toPx().roundToInt()
        val originLang = measurable.first { it.layoutId == TopBarLayout.ORIGIN_LANGUAGE }
            .measure(
                constraints.copy(
                    maxHeight = maxHeight,
                    maxWidth = langWidth
                )
            )
        val iconArrow = measurable.first { it.layoutId == TopBarLayout.ICON_ARROW }
            .measure(
                constraints.copy(
                    maxWidth = maxHeight,
                    maxHeight = maxHeight
                )
            )
        val translationLang = measurable.first { it.layoutId == TopBarLayout.TRANSLATION_LANGUAGE }
            .measure(
                constraints.copy(
                    maxHeight = maxHeight,
                    maxWidth = langWidth
                )
            )

        val layoutWidth = constraints.maxWidth
        val layoutHeight = 48.dp.toPx().roundToInt()
        layout(layoutWidth, layoutHeight) {
            val iconArrowX = (layoutWidth - iconArrow.width) / 2
            val originLangX = (layoutWidth / 2 - iconArrow.width / 2) - originLang.width
            val translationLangX = layoutWidth / 2 + iconArrow.width / 2
            originLang.place(x = originLangX, y = 0)
            iconArrow.place(x = iconArrowX, y = 0)
            translationLang.place(x = translationLangX, y = 0)
        }
    }
}

enum class TopBarLayout {
    ORIGIN_LANGUAGE, ICON_ARROW, TRANSLATION_LANGUAGE
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun PhrasebookLanguagesPreview() {
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
        PhrasebookLanguages(Language(1, "Русский", "rus"))
    }
}