package com.example.publicdictionary.ui.screens

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
import com.example.publicdictionary.ui.navigation.NavRoutes
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

@Composable
fun PublicDictionaryApp(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            PublicDictionaryAppBar(onMenuIconClick = {})
        }
    ) { innerPadding ->
        val viewModel: PhrasebookViewModel = viewModel(factory = PhrasebookViewModel.Factory)
        PhrasebookNavigation(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
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
fun PhrasebookNavigation(
    viewModel: PhrasebookViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TopicList.routes,
        modifier = modifier
    ) {
        composable(
            route = NavRoutes.TopicList.routes
        ) {
            SelectTopicScreen(
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
        composable(
            route = NavRoutes.Topic.routes,
            arguments = NavRoutes.Topic.arguments
        ) {
            PhrasesListScreen(
                viewModel = viewModel,
                topicInput = NavRoutes.Topic.fromEntry(it),
                navController = navController
            )
        }
        composable(
            route = NavRoutes.Phrase.routes,
            arguments = NavRoutes.Phrase.arguments
        ) {
            PhraseScreen(
                viewModel = viewModel,
                phraseInput = NavRoutes.Phrase.fromEntry(it)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicDictionaryAppPreview(){
    PublicDictionaryTheme {
        val navController = rememberNavController()
        PublicDictionaryApp(navController)
    }
}
