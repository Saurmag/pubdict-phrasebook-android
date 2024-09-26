package com.example.presentation_phrasebook.phrase.single

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation_common.state.Error
import com.example.presentation_common.state.Loading
import com.example.presentation_phrasebook.R

@Composable
fun PhraseScreen(
    phraseUiState: PhraseUiState
) {
    phraseUiState.let { uiState ->
        if (uiState.isLoading) {
            Loading()
        }
        if (uiState.exception != null) {
            uiState.exception.localizedMessage?.let { Error(errorMessage = it) }
        }
        if (uiState.phraseModel != null) {
            PhraseDescription(phraseModel = uiState.phraseModel)
        }
    }
}

@Composable
fun PhraseDescription(
    phraseModel: PhraseModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        PhraseOrigin(
            phraseModel = phraseModel,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(
            color = Color(
                red = 0x1D,
                green = 0xC8,
                blue = 0x67,
                alpha = 0x33
            ),
            thickness = 4.dp,
            modifier = Modifier.padding(top = 32.dp)
        )
        PhraseTranslation(
            phraseModel = phraseModel,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun PhraseOrigin(
    phraseModel: PhraseModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = phraseModel.originText,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(text =
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.ipa_text))
                }
                withStyle(style = SpanStyle(color = Color.Gray)){
                    append(stringResource(id = R.string.slash_symbol_tran))
                    append(phraseModel.ipaTextTransliteration)
                    append(stringResource(id = R.string.slash_symbol_tran))
                }
            },
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(text =
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.en_text))
                }
                withStyle(style = SpanStyle(color = Color.Gray)){
                    append(stringResource(id = R.string.slash_symbol_tran))
                    append(phraseModel.enTextTransliteration)
                    append(stringResource(id = R.string.slash_symbol_tran))
                }
            },
            fontSize = 16.sp
        )
    }
}

@Composable
fun PhraseTranslation(
    phraseModel: PhraseModel,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        Text(
            text = phraseModel.translatedText,
            fontSize = 16.sp,
            modifier = Modifier.padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PhraseDescriptionPreview() {
    PhraseDescription(phraseModel = PhraseModel(
        id = 1,
        originText = "На уькнен тӀуьниз вуш заказ гузва?",
        translatedText = "Что ты обычно заказываешь на завтрак?",
        ipaTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?",
        enTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?"
    ))
}

@Preview(
    showBackground = true
)
@Composable
fun PhraseOriginPreview() {
    PhraseOrigin(phraseModel = PhraseModel(
        id = 1,
        originText = "На уькнен тӀуьниз вуш заказ гузва?",
        translatedText = "Что ты обычно заказываешь на завтрак?",
        ipaTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?",
        enTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?"
    ))
}

@Preview(
    showBackground = true
)
@Composable
fun PhraseTranslationPreview() {
    PhraseTranslation(
        phraseModel = PhraseModel(
            id = 1,
            originText = "На уькнен тӀуьниз вуш заказ гузва?",
            translatedText = "Что ты обычно заказываешь на завтрак?",
            ipaTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?",
            enTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?"
        )
    )
}
