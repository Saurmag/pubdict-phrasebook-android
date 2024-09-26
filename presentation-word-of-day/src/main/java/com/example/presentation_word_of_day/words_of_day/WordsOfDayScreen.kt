package com.example.presentation_word_of_day.words_of_day

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation_common.state.Error
import com.example.presentation_common.state.Loading
import com.example.presentation_common.state.PhrasebookLayout
import com.example.presentation_phrasebook.R
import com.example.presentation_word_of_day.swipeablecard.WordsOfDayCardPager
import kotlin.math.roundToInt

@Composable
fun WordsOfDayScreen(
    wordsOfDayUiState: WordsOfDayUiState,
    onPhrasebookTransition: () -> Unit
) {
    wordsOfDayUiState.let { uiState ->
        if (uiState.isLoading) {
           PhrasebookLayout(content = { Loading() })
        }
        if (uiState.exception != null) {
            uiState.exception.localizedMessage?.let { Error(errorMessage = it) }
        }
        if (uiState.wordsOfDay != null) {
            PhrasebookLayout(
                content = {
                    DictionaryScreen(
                        wordsOfDay = uiState.wordsOfDay,
                        onPhrasebookTransition = onPhrasebookTransition
                    )
                }
            )
        }
    }
}

@Composable
fun DictionaryScreen(
    wordsOfDay: List<WordOfDayModel>,
    onPhrasebookTransition: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DictionaryLayout(
        phrasebookTransition = { PhrasebookTransition(onPhrasebookTransition = onPhrasebookTransition) },
        wordsOfDayPager = {
            WordsOfDayCardPager(
                wordsOfDay = wordsOfDay
            )
        },
        testTransition = { TestTransition {} }
    )
}

@Composable
fun PhrasebookTransition(
    modifier: Modifier = Modifier,
    onPhrasebookTransition: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(48.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0x2C, 0xDD, 0x79),
                        Color(0x11, 0xFF, 0xA9)
                    )
                )
            )
            .fillMaxWidth()
            .clickable { onPhrasebookTransition() }
    ) {
        Text(
            text = stringResource(id = R.string.transition_phrasebook),
            fontSize = 16.sp,
            modifier = Modifier.padding(24.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(color = Color.White)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.transition_icon),
                contentDescription = stringResource(id = R.string.transiton_test),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun TestTransition(
    modifier: Modifier = Modifier,
    onTestTransition: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(52.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x2C, 0xDD, 0x79),
                        Color(0x11, 0xFF, 0xA9)
                    )
                )
            )
            .fillMaxWidth()
            .clickable { onTestTransition() }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                )
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.education_icon),
                    contentDescription = stringResource(
                        id = R.string.transiton_test,
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.transition_test_lez),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0x16, 0x16, 0x16)
                )
            }
            Text(
                text = stringResource(id = R.string.transiton_test),
                fontSize = 14.sp,
                color = Color(0x16, 0x16, 0x16, 0xB2)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(48.dp))
                .background(color = Color.White)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.transition_icon),
                contentDescription = stringResource(id = R.string.transiton_test),
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun DictionaryLayout(
    phrasebookTransition: @Composable () -> Unit,
    wordsOfDayPager: @Composable () -> Unit,
    testTransition: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        content = {
            Box(
                modifier = Modifier.layoutId(DictionaryLayout.PhrasebookTransitionLayout)
            ) {
                phrasebookTransition()
            }
            Box(
                modifier = Modifier.layoutId(DictionaryLayout.WordsOfDayLayout)
            ) {
                wordsOfDayPager()
            }
            Box(
                modifier = Modifier.layoutId(DictionaryLayout.TestTransitionLayout)
            ) {
                testTransition()
            }

        },
        measurePolicy = dictionaryMeasure(),
        modifier = Modifier
            .background(
                color = Color.White
            )
    )
}

@Composable
fun dictionaryMeasure(): MeasureScope.(measurables: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val phrasebookTransition = measurables.first { it.layoutId == DictionaryLayout.PhrasebookTransitionLayout }
            .measure(constraints)

        val wordsOfDay = measurables.first { it.layoutId == DictionaryLayout.WordsOfDayLayout }
            .measure(constraints)

        val testTransition = measurables.first { it.layoutId == DictionaryLayout.TestTransitionLayout }
            .measure(constraints)

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        layout(width = layoutWidth, height = layoutHeight) {
            phrasebookTransition.let {
                it.place(
                    x = (layoutWidth - it.width) / 2,
                    y = (layoutHeight * 0.15).roundToInt()
                )
            }
            wordsOfDay.let {
                it.place(
                    x = (layoutWidth - it.width) / 2,
                    y = (layoutHeight * 0.25).roundToInt()
                )
            }
            testTransition.let {
                it.place(
                    x = (layoutWidth - it.width) / 2,
                    y = (layoutHeight * 0.82).roundToInt()
                )
            }
        }
    }
}

enum class DictionaryLayout {
    PhrasebookTransitionLayout, WordsOfDayLayout, TestTransitionLayout
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun WordsOfDayScreenPreview() {
    val wordsOfDay = listOf(
        WordOfDayModel(0, "ваз", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "вад", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "вал", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "чизвани", ImageWordOfDayModel("", 0, 0, 0)),
        WordOfDayModel(0, "унцукуль", ImageWordOfDayModel("", 0, 0, 0))
    )
    WordsOfDayScreen(wordsOfDayUiState = WordsOfDayUiState(wordsOfDay = wordsOfDay), onPhrasebookTransition = {})
}

//@Preview(showBackground = true)
//@Composable
//fun PhrasebookTransitionPreview() {
//    PhrasebookTransition {}
//}
//
//@Preview(showBackground = true)
//@Composable
//fun TestTransitionPreview() {
//    TestTransition {}
//}