package com.example.presentation_phrasebook.phrase.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation_common.state.Error
import com.example.presentation_common.state.Loading
import com.example.presentation_phrasebook.phrase.single.PhraseDescription

@Composable
fun TopicScreen(
    topicUiState: TopicUiState,
    onPhraseClick: (PhraseListItemModel) -> Unit
) {
    topicUiState.let { uiState ->
        if (uiState.isLoading) {
            Loading()
        }
        if (uiState.exception != null) {
            uiState.exception.localizedMessage?.let { Error(errorMessage = it) }
        }
        if (uiState.topicModel != null) {
            PhraseList(
                topicModel = uiState.topicModel,
                onPhraseClick = onPhraseClick
            )
        }
    }
}

@Composable
fun PhraseList(
    topicModel: TopicModel,
    modifier: Modifier = Modifier,
    onPhraseClick: (PhraseListItemModel) -> Unit
) {
    Column(modifier = modifier) {
        TopicName(
            topicModel = topicModel,
            modifier = Modifier.padding(28.dp)
        )
        HorizontalDivider(
            color = Color(
                red = 0x1D,
                green = 0xC8,
                blue = 0x67,
                alpha = 0x33
            ),
            thickness = 4.dp,
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(topicModel.phraseList) { item ->
                Column(
                    modifier = Modifier.padding(12.dp).clickable { onPhraseClick(item) }
                ) {
                    Text(
                        text = item.originText,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(
                                        red = 0x16,
                                        green = 0x16,
                                        blue = 0x16,
                                        alpha = 0x66,
                                    ),
                                )) {
                                append(item.translatedText)
                            }
                        },
                        fontSize = 18.sp,
                    )
                    HorizontalDivider(
                        color = Color(
                            red = 0x00,
                            blue = 0x00,
                            green = 0x00,
                            alpha = 0x19
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopicName(
    topicModel: TopicModel,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = topicModel.originTitle,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 24.dp).fillMaxWidth()
        )
        Text(
            text = topicModel.translatedTitle,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PhraseListPreview() {
    PhraseList(
        topicModel = TopicModel(
            id = 1,
            originTitle = "Начало диалогаlez",
            translatedTitle = "Начало диалога",
            phraseList = listOf(
                PhraseListItemModel(
                    id = 1,
                    originText = "На уькнен тӀуьниз вуш заказ гузва?",
                    translatedText = "Что ты обычно заказываешь на завтрак?"
            ),
                PhraseListItemModel(
                    id = 1,
                    originText = "На уькнен тӀуьниз вуш заказ гузва?",
                    translatedText = "Что ты обычно заказываешь на завтрак?"
                )
            )
        ),
        onPhraseClick = {}
    )
}
