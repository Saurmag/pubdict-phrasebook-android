package com.example.presentation_dictionary.word.single

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
fun WordScreen(
    onBackClick: () -> Unit,
    viewModel: DetailWordViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.collectAsState()
    WordScreen(
        wordUiState = uiState,
        onBackClick = { viewModel.handleEvent(event = WordEvent.BackToDictionary()) },
        onMessageErrorIconClick = { viewModel.handleEvent(event = WordEvent.ShowDialog()) },
        onDismissDialog = { viewModel.handleEvent(event = WordEvent.DismissDialog()) },
        onShareIconClick = {
            uiState.word?.let {
                viewModel.handleEvent(event = WordEvent.ShareWord(it))
            }
        },
        modifier = modifier
    )
    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is WordSideEffect.NavigateToDictionary -> onBackClick()
        }
    }
}

@Composable
internal fun WordScreen(
    wordUiState: WordUiState,
    onBackClick: () -> Unit,
    onMessageErrorIconClick: () -> Unit,
    onDismissDialog: () -> Unit,
    onShareIconClick: () -> Unit,
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
                    wordUiState.isLoading -> Loading()

                    wordUiState.exception != null -> {
                        wordUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                    }

                    wordUiState.word != null -> {
                        WordContent(word = wordUiState.word)

                    }
                }
            },
            modifier = Modifier.consumeWindowInsets(innerPadding)
        )
        if (wordUiState.dialogState) {
            ErrorMessageDialog(onDismissDialog)
        }
    }
}

@Composable
fun WordContent(
    word: DetailWordModel,
    modifier: Modifier = Modifier
) {
    CommonDetailScreen(
        originText = word.text,
        ipaTransliteration = word.ipaTransliteration,
        enTransliteration = word.enTransliteration,
        translation = word.translation,
        recordText = R.string.record_word,
        onRecordClick = { },
        modifier = modifier
    )
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun WordPreview() {
    val wordModel = DetailWordModel(
        text = "Авадан",
        translation = "1. 1) плодородный, урожайный; авадан чил плодородная земля; авадан йис урожайный, плодородный год; \n" +
                "\n" +
                "2) благоустроенный, с достатком (о доме); авадан хуьр гумадилай чир жеда поэт. богатое село узнаётся по дыму (из очагов); авадан авун а) делать плодородной (землю); \n" +
                "\n" +
                "б) благоустраивать, приводить в цветущее состояние (что-л.); авадан хьун а) становиться плодородным, урожайным; б) процветать; становиться благоустроенным, богатым; 2. здание, строение.",
        ipaTransliteration = "veig",
        enTransliteration = "veig"
    )
    PubDictTheme {
        WordScreen(
            wordUiState = WordUiState(word = wordModel),
            onBackClick = {},
            onDismissDialog = {},
            onMessageErrorIconClick = {},
            onShareIconClick = {})
    }
}