package com.example.publicdictionary.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.publicdictionary.R
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.navigation.NavRoutes
import com.example.publicdictionary.ui.navigation.PhraseInput
import com.example.publicdictionary.ui.navigation.TopicInput
import com.example.publicdictionary.ui.theme.Montserrat

@Composable
fun PhrasesListScreen(
    viewModel: PhrasebookViewModel,
    topicInput: TopicInput,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val topic = viewModel.loadTopic(topicInput.topicId)
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.weight(4f)
            )
            IconButton(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.alpha_sort_icon),
                    contentDescription = stringResource(
                        id = R.string.alpha_sort_icon,
                    ),
                )
            }
        }
        SearchPhrase(
            onSearchPhrase = {},
            onSearchIconClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(topic.phrases) { phrase ->
                PhrasesListItem(
                    phrase = phrase,
                    onIconClick = {},
                    onPhraseClick = {
                        navController.navigate(
                            NavRoutes.Phrase.routeForPhrase(PhraseInput(it.id))
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun PhrasesListItem(
    phrase: Phrase,
    onIconClick: () -> Unit,
    onPhraseClick: (Phrase) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = { onPhraseClick(phrase) })
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(4f)
        ) {
            Text(
                text = phrase.text,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Justify
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    ) {
                        append(phrase.enTextTransliteration)
                        append(".  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Thin,
                            fontSize = 14.sp
                        )
                    ) {
                        append(phrase.ipaTextTransliteration)
                    }
                },
                textAlign = TextAlign.Justify
            )
        }
        IconButton(
            onClick = onIconClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favourite_icon),
                contentDescription = stringResource(id = R.string.favourite_icon)
            )
        }
    }
}

@Composable
fun SearchPhrase(
    onSearchPhrase: (String) -> Unit,
    onSearchIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(onClick = onSearchIconClick) {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = stringResource(id = R.string.search_icon)
            )
        }
        TextField(
            value = stringResource(id = R.string.default_text_on_search_phrase),
            onValueChange = onSearchPhrase,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}