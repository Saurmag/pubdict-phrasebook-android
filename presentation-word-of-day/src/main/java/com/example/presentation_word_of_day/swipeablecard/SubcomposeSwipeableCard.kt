package com.example.presentation_word_of_day.swipeablecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.presentation_phrasebook.R
import com.example.presentation_word_of_day.words_of_day.ImageWordOfDayModel
import com.example.presentation_word_of_day.words_of_day.WordOfDayModel

@Composable
fun WordsOfDayCardPager(
    wordsOfDay: List<WordOfDayModel>,
    colorList: List<Color>,
    modifier: Modifier = Modifier,
) {
    var currentItemIndex by rememberSaveable { mutableIntStateOf(0) }
    CardPager(
        items = wordsOfDay,
        modifier = modifier.fillMaxSize(),
        itemContent = {
            for (index in 2 downTo 0) {
                val calculateIndex = calculateIndexForCard(cycleIndex = index, currentItemIndex = currentItemIndex)
                WordOfDayCard(
                    wordOfDayModel = wordsOfDay[calculateIndex],
                    cardIndex = calculateIndex,
                    onCardClick = { cardIndex ->
                        if ( index == 0 ) {
                            currentItemIndex =
                                if (cardIndex == wordsOfDay.size - 1) 0
                                else (cardIndex + wordsOfDay.size - 1) % (wordsOfDay.size - 1) + 1
                        }
                    },
                    color = colorList[calculateIndex],
                    modifier = modifier
                )
            }
        }
    )
}

@Composable
private fun <T> CardPager(
    items: List<T>,
    itemContent: @Composable (element: List<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    
    SubcomposeLayout { constraints ->
        val currentItemsMeasurables = subcompose(
            slotId = null,
            content = { itemContent(items) }
        )
        val currentItemsPlaceables = currentItemsMeasurables.mapIndexed { index, measurable ->
            when(index) {
                0 -> measurable.measure(constraints.copy(maxWidth = (constraints.maxWidth * 0.72).toInt()))
                1 -> measurable.measure(constraints.copy(maxWidth = (constraints.maxWidth * 0.85).toInt()))
                2 -> measurable.measure(constraints)
                else -> measurable.measure(constraints.copy(maxWidth = 0, maxHeight = 0))
            }
        }
        val layoutWidth = constraints.maxWidth
        val layoutHeight = currentItemsPlaceables.first().height + 128
        layout(
            width = layoutWidth,
            height = layoutHeight,
        ) {
            currentItemsPlaceables.forEachIndexed { index, placeable ->
                val xPosition = (constraints.maxWidth - placeable.width) / 2
                val yFrontPosition = 120
                val yNextPosition = 76
                val yPostNextPosition = 40
                when (index) {
                    0 -> { placeable.place(xPosition, yPostNextPosition) }
                    1 -> { placeable.place(xPosition, yNextPosition) }
                    2 -> { placeable.place(xPosition, yFrontPosition) }
                }
            }
        }
    }
}

@Composable
fun WordOfDayCard(
    wordOfDayModel: WordOfDayModel,
    cardIndex: Int,
    onCardClick: (elementIndex: Int) -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onCardClick(cardIndex) },
        shape = RoundedCornerShape(corner = CornerSize(32.dp)),
        modifier = modifier
            .padding(16.dp)
            .height(350.dp)
            .width(350.dp)
    ) {
        Box(contentAlignment = Alignment.BottomStart) {
            if (wordOfDayModel.image.url.isNotBlank()) {
                AsyncImage(
                    model = wordOfDayModel.image.url,
                    contentDescription = stringResource(id = R.string.word_of_day_image),
                    contentScale = ContentScale.Crop
                )
            }
            else {
                Image(
                    painter = ColorPainter(color = color),
                    contentDescription = stringResource(id = R.string.word_of_day_image),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = wordOfDayModel.word,
                fontSize = 32.sp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 32.dp
                )
            )
            Text(
                text = "$cardIndex",
                fontSize = 32.sp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 64.dp
                )
            )
        }
    }
}

internal fun calculateIndexForCard(
    cycleIndex: Int,
    currentItemIndex: Int
): Int {
    return when (cycleIndex) {
        0 -> currentItemIndex
        1 -> if (currentItemIndex == wordsOfDay.size - 1) 0 else currentItemIndex + cycleIndex
        2 -> {
            if (currentItemIndex == wordsOfDay.size - 2) 0
            else if (currentItemIndex > wordsOfDay.size - 2) 1
            else currentItemIndex + cycleIndex
        }
        else -> currentItemIndex
    }
}

@Preview(showBackground = true)
@Composable
private fun WordOfDayPicturePagerPreview() {
    val colorList = listOf(Color.Green, Color.Red, Color.LightGray, Color.Cyan, Color.Black, Color.Magenta)
    WordsOfDayCardPager(wordsOfDay = wordsOfDay, colorList)
}

internal val wordsOfDay = listOf(
    WordOfDayModel(0, "zero", ImageWordOfDayModel("", 0, 0, 0)),
    WordOfDayModel(0, "one", ImageWordOfDayModel("", 0, 0, 0)),
    WordOfDayModel(0, "two", ImageWordOfDayModel("", 0, 0, 0)),
    WordOfDayModel(0, "three", ImageWordOfDayModel("", 0, 0, 0)),
    WordOfDayModel(0, "four", ImageWordOfDayModel("", 0, 0, 0)),
    WordOfDayModel(0, "five", ImageWordOfDayModel("", 0, 0, 0))
)