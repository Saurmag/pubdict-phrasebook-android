package com.example.presentation_phrasebook.topic.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun TopicListScreen(
    topicsUiState: TopicsUiState,
    onTopicClick: (TopicListItemModel) -> Unit
) {
    topicsUiState.let { uiState ->
        if (uiState.isLoading) {
            Loading()
        }
        if (uiState.exception != null) {
            uiState.exception.localizedMessage?.let { Error(errorMessage = it) }
        }
        if (uiState.topicList != null) {
            Topics(
                topics = uiState.topicList,
                onTopicClick = onTopicClick
            )
        }
    }
}

@Composable
fun Topics(
    topics: List<TopicListItemModel>,
    onTopicClick: (TopicListItemModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(topics) { item: TopicListItemModel ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 24.dp)
                    .clickable { onTopicClick(item) }
            ) {
                Text(
                    text = item.originTitle,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.LightGray)) {
                                append(item.translatedTitle)
                            }
                        },
                        fontSize = 14.sp,
                )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Green)) {
                                append(stringResource(id = R.string.count_phrase, item.countPhrase))
                            }
                        },
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxHeight(0.1f)
                    )
                }
                HorizontalDivider(
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TopicListPreview() {
    Topics(
        topics = listOf(
            TopicListItemModel(
                id = 1,
                originTitle = "Чухсагъул",
                translatedTitle = "Благодарность",
                countPhrase = 4
        )),
        onTopicClick = {}
    )
}