package com.example.publicdictionary.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.publicdictionary.R
import com.example.publicdictionary.mockdata.DataSource
import com.example.publicdictionary.mockdata.mockmodel.Phrase
import com.example.publicdictionary.ui.theme.Montserrat
import com.example.publicdictionary.ui.theme.PublicDictionaryTheme

@Composable
fun PhraseScreen(
    phrase: Phrase,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(modifier = modifier.fillMaxWidth()) {
        Text(
            text = phrase.text,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(text = buildAnnotatedString {
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
        })
        Text(text = buildAnnotatedString {
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
        })
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
