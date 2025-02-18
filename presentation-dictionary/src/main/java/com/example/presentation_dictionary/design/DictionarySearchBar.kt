package com.example.presentation_dictionary.design

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation_common.R
import com.example.presentation_common.design.PubDictTheme

@Composable
fun DictionarySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingIconState: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(48.dp))
            .padding(12.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_words),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0x66161616),
                modifier = Modifier.padding(start = 4.dp)
            )
        },
        trailingIcon = {
            if (trailingIconState) {
                TrailingIcon(
                    icon = Icons.Outlined.Search,
                    contentDescription = R.string.search_words_icon_search,
                    color = MaterialTheme.colorScheme.primary,
                    onTrailingIconClick = onTrailingIconClick
                )
            } else {
                TrailingIcon(
                    icon = Icons.Outlined.Close,
                    contentDescription = R.string.search_words_icon_cancel,
                    color = Color.Black,
                    onTrailingIconClick = onTrailingIconClick
                )
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.titleMedium,
        shape = RoundedCornerShape(48.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0x19000000),
            unfocusedBorderColor = Color(0x19000000),
            cursorColor = Color(0xFF161616),
            focusedTextColor = Color.Black
        )
    )
}

@Composable
fun TrailingIcon(
    icon: ImageVector,
    @StringRes contentDescription: Int,
    color: Color,
    onTrailingIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, radius = Dp.Unspecified),
                role = Role.Button,
            ) {
                onTrailingIconClick()
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = contentDescription),
            tint = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DictionarySearchBarPreview() {
    PubDictTheme {
        Box() {
            DictionarySearchBar(
                onTrailingIconClick = { },
                query = "",
                onQueryChange = {}
            )
        }
    }
}