package com.example.presentation_dictionary.word.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.presentation_common.design.Error
import com.example.presentation_common.design.Loading

@Composable
fun WordList(
    words: LazyPagingItems<WordModel>,
    onWordClick: (String) -> Unit,
    pointEventEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 4.dp, bottom = 32.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = pointEventEnabled,
        modifier = modifier
    ) {
        items(count = words.itemCount, contentType = { words[it] }) { wordIndex ->
            words[wordIndex]?.let { word ->
                Word(
                    word = word.originText,
                    onWordClick = onWordClick,
                    clickEnabled = pointEventEnabled
                )
            }
        }
        item {
            when (words.loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> Loading()
                is LoadState.Error -> {
                    (words.loadState.append as LoadState.Error).error.localizedMessage?.let {
                        Error(errorMessage = it)
                    }
                }
            }
        }
    }
}

@Composable
fun Word(
    word: String,
    onWordClick: (String) -> Unit,
    clickEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                ),
                enabled = clickEnabled
            ) { onWordClick(word) }
    ) {
        Text(
            text = word,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
        )
        HorizontalDivider(
            color = Color(0x19000000)
        )
    }
}