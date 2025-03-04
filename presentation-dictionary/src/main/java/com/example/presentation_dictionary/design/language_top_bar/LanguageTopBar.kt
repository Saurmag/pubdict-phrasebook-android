package com.example.presentation_dictionary.design.language_top_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
    val tranLangState = remember { mutableStateOf(TranslationLanguageState.Default) }
    val visible = remember { mutableStateOf(false) }
    val animatableWidth = remember {
        Animatable(initialValue = LanguageSize.Medium.width.value)
    }
    val scope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth()
    ) {
        OriginLanguage(
            originLang = com.example.presentation_common.R.string.phrasebook_origin_lang,
            modifier = Modifier.width(LanguageSize.Medium.width)
        )
        ArrowIcon(
            resource = R.drawable.transition_icon, R.string.transiton_test,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Column {
            TranslationLanguage(
                language = language,
                tranLangState = tranLangState,
                onTranslationLanguageClick = {
                    tranLangState.value =
                        if (tranLangState.value == TranslationLanguageState.Default) TranslationLanguageState.Select
                        else TranslationLanguageState.Default
                    visible.value = !visible.value
                    scope.launch {
                        val targetValue = if (tranLangState.value != TranslationLanguageState.Default) 180.dp.value else LanguageSize.Medium.width.value
                        animatableWidth.animateTo(targetValue = targetValue)
                    }
                },
                modifier = Modifier
                    .width(animatableWidth.value.dp)
            )
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
                            if (tranLangState.value == TranslationLanguageState.Default) TranslationLanguageState.Select
                            else TranslationLanguageState.Default
                        visible.value = !visible.value
                        scope.launch { animatableWidth.animateTo(targetValue = LanguageSize.Medium.width.value) }
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }
        }
    }
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
