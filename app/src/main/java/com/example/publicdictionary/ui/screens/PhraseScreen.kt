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
import com.example.publicdictionary.R
import com.example.publicdictionary.ui.navigation.PhraseInput
import com.example.publicdictionary.ui.theme.Montserrat

@Composable
fun PhraseScreen(
    viewModel: PhrasebookViewModel,
    phraseInput: PhraseInput,
    modifier: Modifier = Modifier
) {
    val phrase = viewModel.loadPhrase(phraseId = phraseInput.phraseId)
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