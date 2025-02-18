package com.example.presentation_dictionary.design.language_top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation_dictionary.dictionary.LanguageModel

@Composable
fun TranslationLanguageList(
    translationLangList: List<LanguageModel>,
    onTranslationLanguageItemClick: (LanguageModel) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val horizontalDividerModifier = Modifier.width(width = LanguageSize.Medium.width)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawWithContent {
                val behindShadowX = (this.size.width * -0.005).toFloat()
                val behindShadowY = (this.size.height * -0.005).toFloat()
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x40000000),
                            Color.White
                        ),
                        radius = (this.size.width * 0.8).toFloat(),
                        center = Offset.Unspecified
                    ),
                    cornerRadius = CornerRadius(x = 56f, y = 56f),
                    topLeft = Offset(behindShadowX, behindShadowY),
                    size = this.size.copy(
                        width = (this.size.width * 1.01).toFloat(),
                        height = (this.size.height * 1.01).toFloat()
                    )
                )
                drawContent()
            }
            .clip(RoundedCornerShape(CornerSize(20.dp)))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }
    ) {
        translationLangList.forEachIndexed { index, tranLang ->
            key(index) {
                TranslationLanguageListItem(
                    translationLanguage = tranLang,
                    onTranslationLanguageItemClick = { onTranslationLanguageItemClick(tranLang) }
                )
                if (index != translationLangList.lastIndex) {
                    HorizontalDivider(
                        color = Color(0x19000000),
                        thickness = 1.dp,
                        modifier = horizontalDividerModifier
                    )
                }
            }
        }
    }
}

@Composable
fun TranslationLanguageListItem(
    translationLanguage: LanguageModel,
    modifier: Modifier = Modifier,
    onTranslationLanguageItemClick: (LanguageModel) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .size(width = LanguageSize.Medium.width, height = LanguageSize.Medium.height)
            .background(
                color = Color.White,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onTranslationLanguageItemClick(translationLanguage) }
    ) {
        Text(
            text = translationLanguage.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TranslationListPreview() {
    val tranLangList = listOf(
        LanguageModel(1, "Русский", "rus"),
        LanguageModel(1, "Турецкий", "rus"),
        LanguageModel(1, "Английский", "rus")
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(300.dp)
            .background(color = Color.White)
    ) {
        TranslationLanguageList(translationLangList = tranLangList)
    }
}

@Preview(showBackground = true)
@Composable
fun TranslationListItemPreview() {
    val tranLang = LanguageModel(1, "Русский", "rus")
    TranslationLanguageListItem(tranLang)
}