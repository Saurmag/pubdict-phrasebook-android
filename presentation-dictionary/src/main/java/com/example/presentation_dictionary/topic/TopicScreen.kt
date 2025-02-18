package com.example.presentation_dictionary.topic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation_common.design.DictionaryLayoutWithDraw
import com.example.presentation_common.design.Error
import com.example.presentation_common.design.Loading
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_common.design.TopicTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun TopicScreen(
    viewModel: TopicViewModel,
    onBackClick: () -> Unit,
    onPhraseClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.collectAsState()
    TopicScreen(
        topicUiState = uiState,
        onPhraseClick = { viewModel.handleEvent(event = TopicEvent.SelectPhrase(it)) },
        onBackClick = { viewModel.handleEvent(event = TopicEvent.BackToDictionary()) },
        modifier = modifier
    )
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TopicSideEffect.BackToDictionary -> onBackClick()
            is TopicSideEffect.NavigateToPhrase -> onPhraseClick(sideEffect.phraseId)
        }
    }
}

@Composable
internal fun TopicScreen(
    topicUiState: TopicUiState,
    onPhraseClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopicTopBar(
                onBackClick = onBackClick,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        },
        containerColor = Color.Transparent,
        modifier = modifier
    ) { innerPadding ->
        DictionaryLayoutWithDraw(
            content = {
                when {
                    topicUiState.isLoading -> Loading()

                    topicUiState.exception != null -> {
                        topicUiState.exception.localizedMessage?.let { Error(errorMessage = it) }
                    }

                    topicUiState.topicModel != null -> {
                        Topic(
                            topicModel = topicUiState.topicModel,
                            onPhraseClick = onPhraseClick,
                            modifier = Modifier
                                .consumeWindowInsets(innerPadding)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun Topic(
    topicModel: TopicModel,
    modifier: Modifier = Modifier,
    onPhraseClick: (Int) -> Unit
) {
    Column(modifier) {
        TopicName(
            topicModel = topicModel,
            modifier = Modifier.padding(top = 20.dp)
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            thickness = 4.dp,
        )
        LazyColumn(
            contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp, start = 32.dp, end = 32.dp)
        ) {
            items(topicModel.phraseList) { topic ->
                TopicItem(topic = topic, onPhraseClick = onPhraseClick)
            }
            item {
                Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars))
            }
        }
    }
}

@Composable
fun TopicName(
    topicModel: TopicModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 28.dp, bottom = 28.dp)) {
        Text(
            text = topicModel.originTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        )
        Text(
            text = topicModel.translatedTitle,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TopicItem(
    topic: TopicItemModel,
    onPhraseClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                )
            ) { onPhraseClick(topic.id) }
    ) {
        Text(
            text = topic.originText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
        )
        Text(
            text = topic.translatedText,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0x66161616),
        )
        HorizontalDivider(
            color = Color(0x19000000),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_7"
)
@Composable
fun TopicPreview() {
    PubDictTheme {
        TopicScreen(
            topicUiState = TopicUiState(
                topicModel = TopicModel(
                    id = 1,
                    originTitle = "Начало диалогаlez",
                    translatedTitle = "Начало диалога",
                    phraseList = listOf(
                        TopicItemModel(
                            id = 1,
                            originText = "На уькнен тӀуьниз вуш заказ гузва?",
                            translatedText = "Что ты обычно заказываешь на завтрак?"
                        ),
                        TopicItemModel(
                            id = 1,
                            originText = "Ваз уйунар кьугъаз чидани?",
                            translatedText = "Что ты обычно заказываешь на завтрак?"
                        )
                    )
                )
            ),
            onPhraseClick = {},
            onBackClick = {}
        )
    }
}
