package com.example.presentation_dictionary.phrase

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation_common.design.CommonDetailScreen
import com.example.presentation_common.design.CommonDetailTopBar
import com.example.presentation_common.design.DictionaryLayoutWithDraw
import com.example.presentation_common.design.Error
import com.example.presentation_common.design.ErrorMessageDialog
import com.example.presentation_common.design.Loading
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PhraseScreen(
    onBackClick: () -> Unit,
    viewModel: PhraseViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.collectAsState()
    PhraseScreen(
        phraseUiState = uiState,
        onBackClick = { viewModel.handleEvent(PhraseEvent.BackToTopic()) },
        onShareIconClick = {
            uiState.phraseModel?.let {
                viewModel.handleEvent(event = PhraseEvent.SharePhrase(it))
            }
        },
        onMessageErrorIconClick = { viewModel.handleEvent(PhraseEvent.ShowDialog()) },
        onDismissDialog = { viewModel.handleEvent(PhraseEvent.DismissDialog()) },
        modifier = modifier
    )
    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is PhraseSideEffect.NavigateToTopic -> onBackClick()
        }
    }
}

@Composable
fun PhraseScreen(
    phraseUiState: PhraseUiState,
    onBackClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onMessageErrorIconClick: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CommonDetailTopBar(
                onBackClick = onBackClick,
                onBookmarkIconClick = {},
                onShareIconClick = onShareIconClick,
                onMessageErrorIconClick = onMessageErrorIconClick,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        containerColor = Color.Transparent,
        modifier = modifier
    ) { innerPadding ->
        DictionaryLayoutWithDraw(
            content = {
                when {
                    phraseUiState.isLoading -> Loading()

                    phraseUiState.exception != null -> {
                        phraseUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                    }

                    phraseUiState.phraseModel != null -> {
                        Phrase(phrase = phraseUiState.phraseModel)
                    }
                }
            },
            modifier = Modifier.consumeWindowInsets(innerPadding)
        )
        if (phraseUiState.dialogState) {
            ErrorMessageDialog(onDismissDialog)
        }
    }
}

@Composable
fun Phrase(
    phrase: PhraseModel,
    modifier: Modifier = Modifier,
    onRecordCLick: () -> Unit = {},
) {
    CommonDetailScreen(
        originText = phrase.originText,
        ipaTransliteration = phrase.ipaTextTransliteration,
        enTransliteration = phrase.enTextTransliteration,
        translation = phrase.translatedText,
        recordText = R.string.record_phrase,
        onRecordClick = onRecordCLick,
        modifier = modifier
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PhraseDescriptionPreview() {
    PubDictTheme {
        PhraseScreen(
            phraseUiState = PhraseUiState(
                phraseModel = PhraseModel(
                    id = 0,
                    originText = "На уькнен тӀуьниз вуш заказ гузва?",
                    translatedText = "Что ты обычно заказываешь на завтрак?",
                    ipaTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?",
                    enTextTransliteration = "na ükʰnen t'üniz wuʃ zakʰaz guzwa?"
                )
            ),
            onShareIconClick = {},
            onBackClick = {},
            onDismissDialog = {},
            onMessageErrorIconClick = {}
        )
    }
}