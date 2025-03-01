package com.example.presentation_dictionary.phrasebook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.R

@Composable
fun Phrasebook(
    topics: List<PhrasebookItemModel>,
    onTopicClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp, start = 32.dp, end = 32.dp),
        modifier = modifier.imePadding()
    ) {
        items(topics) { topic: PhrasebookItemModel ->
            Topic(
                topic = topic,
                onTopicClick = { onTopicClick(topic.id) }
            )
        }
        item {
            Spacer(
                modifier = Modifier.windowInsetsTopHeight(WindowInsets.systemBars)
            )
        }
    }
}

@Composable
fun Topic(
    topic: PhrasebookItemModel,
    onTopicClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                ),
            ) { onTopicClick() }
    ) {
        Text(
            text = topic.originTitle,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0x66161616),
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontFamily = MaterialTheme.typography.labelMedium.fontFamily
                        )
                    ) {
                        append(topic.translatedTitle)
                    }
                }
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontFamily = MaterialTheme.typography.labelMedium.fontFamily
                        )
                    ) {
                        append(stringResource(id = R.string.count_phrase, topic.countPhrase))
                    }
                }
            )
        }
        HorizontalDivider(
            color = Color(0x19000000),
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TopicListPreview() {
    PubDictTheme {
        Phrasebook(
            topics = listOf(
                PhrasebookItemModel(
                    id = 1,
                    originTitle = "Чухсагъул",
                    translatedTitle = "Благодарность",
                    countPhrase = 4
                ),
                PhrasebookItemModel(
                    id = 1,
                    originTitle = "Сармак",
                    translatedTitle = "Благодарность",
                    countPhrase = 4
                ),
                PhrasebookItemModel(
                    id = 1,
                    originTitle = "Адждах1а",
                    translatedTitle = "Благодарность",
                    countPhrase = 4
                ),
                PhrasebookItemModel(
                    id = 1,
                    originTitle = "Лечо",
                    translatedTitle = "Благодарность",
                    countPhrase = 4
                )
            ),
            onTopicClick = {}
        )
    }
}