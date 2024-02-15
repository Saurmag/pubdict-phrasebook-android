package com.example.publicdictionary.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    phrases: List<Phrase>,
    modifier: Modifier = Modifier
) {
    Column {
        LazyColumn(modifier = modifier) {
            items(phrases) { phrase ->
                PhrasesListItem(
                    phrase = phrase.text,
                    phraseInRequiredLanguage = phrase.textTranslation,
                    phraseInEnglishTransliteration = phrase.enTextTransliteration,
                    onIconClick = { /*TODO*/ }
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
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
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
                    painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                    contentDescription = stringResource(id = R.string.favourite_icon)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhrasesListScreenPreview() {
    PublicDictionaryTheme {
        val phrases = DataSource.japanesePhrasebook.topics[0].phrases
        PhrasesListScreen(phrases = phrases)
    }
}