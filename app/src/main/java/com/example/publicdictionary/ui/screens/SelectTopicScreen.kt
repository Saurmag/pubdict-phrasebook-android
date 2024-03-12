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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.publicdictionary.R
import com.example.publicdictionary.ui.model.Topic
import com.example.publicdictionary.ui.navigation.NavRoutes
import com.example.publicdictionary.ui.navigation.TopicInput

@Composable
fun SelectTopicScreen(
    viewModel: PhrasebookViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    viewModel.phrasebookUiState.collectAsState().value.let { state ->
        when(state.isLoading) {
            true -> {
                viewModel.getPhrasebook()
            }
            false -> {
                val phrasebook = state.phrasebook
                if (phrasebook == null) {
                    viewModel.getPhrasebook()
                } else {
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
                            items(phrasebook.topics) { topic ->
                                TopicItem(
                                    topic = topic,
                                    onTopicClick = {
                                        navController.navigate(
                                            NavRoutes.Topic.routeForTopic(
                                                TopicInput(topicId = it.id)
                                            )
                                        )
                                    },
                                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TopicItem(
    topic: Topic,
    onTopicClick: (Topic) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .clickable(onClick = { onTopicClick(topic) })
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.padding_small)
                )
            )
            Text(
                text = stringResource(id = R.string.count_phrases, topic.countPhrases),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = stringResource(
                    R.string.preview_topic_phrase,
                    topic.phrases[0].enTextTransliteration
                ),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}