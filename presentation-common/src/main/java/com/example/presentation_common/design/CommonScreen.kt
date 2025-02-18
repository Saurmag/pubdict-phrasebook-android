package com.example.presentation_common.design

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Error(errorMessage: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Snackbar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            containerColor = MaterialTheme.colorScheme.error
        ) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.onError)
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun DictionaryLayoutWithDraw(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LocalGradientColors.current.bottom,
                        LocalGradientColors.current.top
                    )
                )
            )
            .drawBehind {
                translate(
                    top = (size.height * 0.15).toFloat()
                ) {
                    drawRoundRect(
                        color = backgroundColor,
                        cornerRadius = CornerRadius(
                            x = 90f,
                            y = 90f
                        )
                    )
                }
            }
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DictionaryLayoutWithDrawPreview() {
    PubDictTheme {
        DictionaryLayoutWithDraw(content = {})
    }
}