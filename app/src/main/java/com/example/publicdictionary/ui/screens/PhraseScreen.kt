package com.example.publicdictionary.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.publicdictionary.R
import com.example.publicdictionary.mockdata.DataSource
import com.example.publicdictionary.ui.model.Phrase
import com.example.publicdictionary.ui.theme.Montserrat
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

@Composable
fun PhraseScreen(
    phrase: Phrase,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(id = R.string.phrase, phrase.text),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(context.getString(R.string.ipa))
                    append(" ")
                }
                withStyle(style = SpanStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    append(context.getString(R.string.slash))
                    append(" ")
                    append(phrase.ipaTextTransliteration)
                    append(" ")
                    append(context.getString(R.string.slash))
                }
            },
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small)
            )
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                )
                ) {
                    append(context.getString(R.string.en))
                    append(" ")
                }
                withStyle(style = SpanStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
                ) {
                    append(context.getString(R.string.slash))
                    append(" ")
                    append(phrase.enTextTransliteration)
                    append(" ")
                    append(context.getString(R.string.slash))
                }
            },
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_small),
                bottom = dimensionResource(id = R.dimen.padding_small)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhraseScreenPreview() {
    PublicDictionaryTheme {
        val phrase = DataSource.japanesePhrasebook.topics[0].phrases[0]
        PhraseScreen(phrase = phrase)
    }
}
