package com.example.presentation_dictionary.design.swipeablecard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.presentation_common.design.LocalGradientColors
import com.example.presentation_common.design.PubDictTheme
import com.example.presentation_dictionary.R
import com.example.presentation_dictionary.words_of_day.ImageWordOfDayModel
import com.example.presentation_dictionary.words_of_day.WordOfDayModel

@Composable
fun WordsOfDayCardPager(
    wordsOfDay: List<WordOfDayModel>,
    onCardClick: (String) -> Unit,
    onShareIconClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (wordsOfDay.size > 1) {
        CycleCardPager(
            items = wordsOfDay,
            onCardClick = { onCardClick(it.word) }
        ) {
            WordOfDayCard(
                wordOfDayModel = it,
                clickEnable = false,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    else if (wordsOfDay.isNotEmpty()) {
        val wordOfDay = wordsOfDay[0]
        WordOfDayCard(
            wordOfDayModel = wordOfDay,
            clickEnable = true,
            onCardClick = onCardClick,
            onShareIconClick = onShareIconClick,
            modifier = modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun WordOfDayCard(
    wordOfDayModel: WordOfDayModel,
    clickEnable: Boolean,
    onCardClick: (String) -> Unit = {},
    onShareIconClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(corner = CornerSize(32.dp)))
            .pointerInput(key1 = Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                    val up = waitForUpOrCancellation()
                    if (up != null && clickEnable) {
                        onCardClick(wordOfDayModel.word)
                    }
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            if (wordOfDayModel.image.url.isNotBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(wordOfDayModel.image.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.word_of_day_image),
                    placeholder = BrushPainter(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                LocalGradientColors.current.bottom,
                                LocalGradientColors.current.top
                            )
                        )
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = BrushPainter(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                LocalGradientColors.current.bottom,
                                LocalGradientColors.current.top
                            )
                        )
                    ),
                    contentDescription = stringResource(id = R.string.word_of_day_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = wordOfDayModel.word,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                    )
                    CardActionIcon(
                        description = R.string.bookmark,
                        icon = R.drawable.bookmark_icon,
                        onIconClick = {}
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    AdditionalMark()
                    CardActionIcon(
                        description = R.string.share,
                        icon = R.drawable.share_icon,
                        onIconClick = {
                            onShareIconClick(wordOfDayModel.word)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun AdditionalMark(modifier: Modifier = Modifier) {
    CommonIconContainer(
        modifier = modifier,
        onIconClick = {}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pub_dic_icon),
                contentDescription = stringResource(id = R.string.pub_dict),
                tint = Color.White,
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.pub_dict),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
        }
    }
}

@Composable
fun CardActionIcon(
    @StringRes description: Int,
    @DrawableRes icon: Int,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CommonIconContainer(
        modifier = modifier.padding(bottom = 12.dp),
        onIconClick = onIconClick
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = description),
            tint = Color.White,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun CommonIconContainer(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(corner = CornerSize(12.dp)),
                color = Color(0x40FFFFFF)
            )
            .clickable(
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                )
            ) { onIconClick() }
    ) {
        content()
    }
}

@Preview
@Composable
fun WordOfDayCardPreview() {
    PubDictTheme {
        Box(modifier = Modifier) {
            WordOfDayCard(
                clickEnable = true,
                wordOfDayModel = WordOfDayModel(0, "B", ImageWordOfDayModel("", 0, 0, 0)),
                onShareIconClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordOfDayCardPagerPreview() {
    val wordsOfDay = List(size = 3) { WordOfDayModel(0, "B", ImageWordOfDayModel("", 0, 0, 0)) }
    PubDictTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WordsOfDayCardPager(
                wordsOfDay = wordsOfDay,
                onCardClick = {},
                onShareIconClick = {}
            )
        }
    }
}