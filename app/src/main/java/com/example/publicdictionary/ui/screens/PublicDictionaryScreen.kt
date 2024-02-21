package com.example.publicdictionary.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.publicdictionary.R
import com.example.publicdictionary.ui.model.Phrasebook
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

enum class PublicDictionaryScreen {
    SelectTopic,
    SelectPhrase,
    Phrase
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicDictionaryAppBar(
    onMenuIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mountain_image),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(46.dp)
                )
                Column(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(
                        text = stringResource(id = R.string.lezgin),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = R.string.phrasebook),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onMenuIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vertical),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun PublicDictionaryApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            PublicDictionaryAppBar(onMenuIconClick = {})
        }
    ) { innerPadding ->
        val viewModel: PhrasebookViewModel = viewModel(factory = PhrasebookViewModel.Factory)
        val uiState = viewModel.phrasebookUiState
        Log.d("PUBLIC_DICTIONARY_SCREEN", "${viewModel.phrasebookUiState.phrasebook}")
        when(uiState.isLoading) {
            true -> viewModel.getPhrasebook()
            false -> {
                if (uiState.phrasebook == null) {
                    viewModel.getPhrasebook()
                } else {
                    PhrasebookNavigation(
                        phrasebook = uiState.phrasebook,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PhrasebookNavigation(
    phrasebook: Phrasebook,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PublicDictionaryScreen.SelectTopic.name,
        modifier = modifier
    ) {
        composable(
            route = PublicDictionaryScreen.SelectTopic.name
        ) {
            SelectTopicScreen(
                topics = phrasebook.topics,
                onTopicClick = {
                    navController.navigate(route = PublicDictionaryScreen.SelectPhrase.name)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
        composable(
            route = PublicDictionaryScreen.SelectPhrase.name
        ) {
            PhrasesListScreen(
                topicTitle = phrasebook.topics[0].title,
                phrases = phrasebook.topics[0].phrases,
                onAlphaSortIconClick = {},
                onItemClick = {
                    navController.navigate(route = PublicDictionaryScreen.Phrase.name)
                },
                onSearchIconClick = {},
                onIconClick = {},
                onSearchPhrase = {}
            )
        }
        composable(
            route = PublicDictionaryScreen.Phrase.name
        ) {
            PhraseScreen(phrase = phrasebook.topics[0].phrases[0])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicDictionaryAppPreview(){
    PublicDictionaryTheme {
        PublicDictionaryApp()
    }
}
