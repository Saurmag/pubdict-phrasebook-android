package com.example.presentation_common.design

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation_common.R

@Composable
fun CommonDetailTopBar(
    onBackClick: () -> Unit,
    onBookmarkIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onMessageErrorIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        BackIconButton(onBackClick = onBackClick)
        Spacer(modifier = Modifier.fillMaxWidth(0.3f))
        CommonTopBarIconButton(
            drawable = R.drawable.share_icon,
            description = R.string.share_icon,
            onIconClick = onShareIconClick
        )
        CommonTopBarIconButton(
            drawable = R.drawable.message_icon,
            description = R.string.message_error_icon,
            onIconClick = onMessageErrorIconClick
        )
        CommonTopBarIconButton(
            drawable = R.drawable.bookmark_icon,
            description = R.string.bookmark_icon,
            onIconClick = onBookmarkIconClick
        )
    }
}

@Composable
fun TopicTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        BackIconButton(onBackClick = onBackClick)
    }
}

@Composable
fun BackIconButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(start = 20.dp)
            .background(
                shape = RoundedCornerShape(48.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            .clip(shape = RoundedCornerShape(48.dp))
            .clickable { onBackClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 12.dp,
                end = 20.dp,
                top = 12.dp,
                bottom = 12.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = stringResource(id = R.string.back_icon),
                tint = LocalTintTheme.current.iconTint,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.back_icon),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun CommonTopBarIconButton(
    @DrawableRes drawable: Int,
    @StringRes description: Int,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .clip(shape = RoundedCornerShape(CornerSize(20.dp)))
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    radius = Dp.Unspecified
                ),
                role = Role.Button
            ) { onIconClick() }
    ) {
        Icon(
            painter = painterResource(id = drawable),
            contentDescription = stringResource(id = description),
            modifier = modifier
                .padding(12.dp)
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun CommonDetailTopBarPreview() {
    PubDictTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LocalGradientColors.current.bottom,
                            LocalGradientColors.current.top
                        )
                    )
                )
                .fillMaxWidth()
                .height(200.dp)
        ) {
            CommonDetailTopBar(
                onBackClick = {},
                onBookmarkIconClick = {},
                onMessageErrorIconClick = {},
                onShareIconClick = {}
            )
        }
    }
}