package com.example.publicdictionary.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.publicdictionary.R
import com.example.publicdictionary.mockdata.DataSource
import com.example.publicdictionary.ui.model.Topic
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

@Composable
fun SelectTopicScreen(
    topics: List<Topic>,
    onTopicClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.topics),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
        )
        LazyColumn(
            modifier = modifier
        ) {
            items(topics) { topic ->
                TopicItem(
                    title = topic.title,
                    countPhrase = topic.countPhrases,
                    previewWord = topic.phrases[0].textTranslation,
                    onTopicClick = onTopicClick,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun TopicItem(
    title: String,
    countPhrase: Int,
    previewWord: String,
    onTopicClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .clickable(onClick = onTopicClick)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            )
            Text(
                text = stringResource(id = R.string.count_phrases, countPhrase),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = stringResource(
                    R.string.preview_topic_phrase,
                    previewWord
                ),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectTopicScreenPreview() {
    val topics = DataSource.japanesePhrasebook.topics
    PublicDictionaryTheme {
        SelectTopicScreen(
            topics,
            onTopicClick = {}
        )
    }
}