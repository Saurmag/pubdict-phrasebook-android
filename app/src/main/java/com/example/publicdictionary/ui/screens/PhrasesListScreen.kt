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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.publicdictionary.R
import com.example.publicdictionary.mockdata.DataSource
import com.example.publicdictionary.mockdata.mockmodel.Phrase
import com.example.publicdictionary.ui.theme.Montserrat
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

@Composable
fun PhrasesListScreen(
    topicTitle: String,
    phrases: List<Phrase>,
    onAlphaSortIconClick: () -> Unit,

    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = topicTitle,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.weight(4f)
            )
            IconButton(
                onClick = onAlphaSortIconClick,
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
            items(phrases) { phrase ->
                PhrasesListItem(
                    phrase = phrase.text,
                    phraseInRequiredLanguage = phrase.textTranslation,
                    phraseInEnglishTransliteration = phrase.enTextTransliteration,
                    onIconClick = {},
                    onItemClick = {}
                )
            }
        }
    }
}

@Composable
fun PhrasesListItem(
    phrase: String,
    phraseInRequiredLanguage: String,
    phraseInEnglishTransliteration: String,
    onIconClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = onItemClick)
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(4f)
        ) {
            Text(
                text = phrase,
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
                        append(phraseInRequiredLanguage)
                        append(".  ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Thin,
                            fontSize = 14.sp
                        )
                    ) {
                        append(phraseInEnglishTransliteration)
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
            modifier = Modifier.fillMaxWidth().weight(8f)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPhrasePreview() {
    PublicDictionaryTheme {
        SearchPhrase(
            onSearchPhrase = {},
            onSearchIconClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhrasesListScreenPreview() {
    PublicDictionaryTheme {
        val topic = DataSource.japanesePhrasebook.topics[0]
        PhrasesListScreen(
            topicTitle = topic.title,
            phrases = topic.phrases,
            onAlphaSortIconClick = {}
        )
    }
}