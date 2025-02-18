package com.example.presentation_common.design

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.presentation_common.R

@Composable
fun CommonDetailScreen(
    originText: String,
    ipaTransliteration: String,
    enTransliteration: String,
    translation: String,
    @StringRes recordText: Int,
    onRecordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 24.dp)
    ) {
        Origin(
            originText = originText,
            ipaTransliteration = ipaTransliteration,
            enTransliteration = enTransliteration,
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            thickness = 4.dp,
            modifier = Modifier.padding(top = 32.dp)
        )
        Translation(
            translation = translation,
            onRecordClick = onRecordClick,
            recordText = recordText,
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun Origin(
    originText: String,
    ipaTransliteration: String,
    enTransliteration: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = originText,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(6f)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(40.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .weight(weight = 2f, fill = false)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.volume_icon),
                    contentDescription = stringResource(id = R.string.volume_icon),
                    tint = Color.White
                )
            }
        }
        Text(
            text =
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(id = R.string.ipa_text))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Gray
                    )
                ) {
                    append(stringResource(id = R.string.slash_symbol_tran))
                    append(ipaTransliteration)
                    append(stringResource(id = R.string.slash_symbol_tran))
                }
            },
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(
            text =
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(id = R.string.en_text))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.Gray
                    )
                ) {
                    append(stringResource(id = R.string.slash_symbol_tran))
                    append(enTransliteration)
                    append(stringResource(id = R.string.slash_symbol_tran))
                }
            }
        )
    }
}

@Composable
fun Translation(
    translation: String,
    onRecordClick: () -> Unit,
    @StringRes recordText: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = translation,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth()
        )
        RecordButton(
            onRecordClick = onRecordClick,
            recordText = recordText,

            )
        Spacer(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
        )
    }
}

@Composable
fun RecordButton(
    onRecordClick: () -> Unit,
    @StringRes recordText: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onRecordClick,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        LocalGradientColors.current.bottom,
                        LocalGradientColors.current.top
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp,
                top = 12.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.microphone_icon),
                contentDescription = stringResource(id = recordText),
                tint = Color.Black
            )
            Text(
                text = stringResource(id = recordText),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
fun ErrorMessageDialog(
    onDismissRequest: () -> Unit
) {
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color(red = 0xFA, green = 0xFB, blue = 0xFC),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.Black,
                    contentDescription = stringResource(R.string.dialog_cancel_icon),
                    modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            clip = true,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onDismissRequest() }
                        .padding(10.dp)
                )
            }
            OutlinedTextField(
                value = errorMessage,
                onValueChange = { errorMessage = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.dialog_error_message_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(red = 0xAC, green = 0xAD, blue = 0xB3)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = 2.dp,
                        color = Color(red = 0x00, green = 0x00, blue = 0x00, alpha = 0x19),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        context.getString(R.string.not_implemented),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.dialog_send_error_message_button),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun CommonDetailScreenPreview() {
    PubDictTheme {
        Box(modifier = Modifier.fillMaxHeight()) {
            val text = "Авадан"
            val translation =
                "1. 1) плодородный, урожайный; авадан чил плодородная земля; авадан йис урожайный, плодородный год; \n" +
                        "\n" +
                        "2) благоустроенный, с достатком (о доме); авадан хуьр гумадилай чир жеда поэт. богатое село узнаётся по дыму (из очагов); авадан авун а) делать плодородной (землю); \n" +
                        "\n" +
                        "б) благоустраивать, приводить в цветущее состояние (что-л.); авадан хьун а) становиться плодородным, урожайным; б) процветать; становиться благоустроенным, богатым; 2. здание, строение."
            val ipaTransliteration = "veig"
            val enTransliteration = "veig"

            CommonDetailScreen(
                originText = text,
                ipaTransliteration = ipaTransliteration,
                enTransliteration = enTransliteration,
                translation = translation,
                recordText = R.string.volume_icon,
                onRecordClick = { }
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_7")
@Composable
fun ErrorMessageDialogPreview() {
    PubDictTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ErrorMessageDialog {}
        }
    }
}