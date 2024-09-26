package com.example.presentation_common.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun Error(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        CircularProgressIndicator(
            color = Color(0x11, 0xFF, 0xA9)
        )
    }
}

@Composable
fun PhrasebookLayout(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Layout(
        content = {
            content()
        },
        measurePolicy = phrasebookMeasure(),
        modifier = Modifier
            .offset(y = 0.dp.plus((screenHeight * 0.2).dp))
            .background(
                shape = RoundedCornerShape(
                    topStart = 36.dp,
                    topEnd = 36.dp
                ),
                color = Color.White
            )

    )
}

@Composable
fun phrasebookMeasure(): MeasureScope.(measurables: List<Measurable>, constraints: Constraints) -> MeasureResult {
    return { measurables, constraints ->
        val content = measurables.map {
            it.measure(constraints)
        }

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        layout(width = layoutWidth, height = layoutHeight) {
            content.forEach() {
                it.place(
                    x = (layoutWidth - it.width) / 2,
                    y = (layoutHeight * -0.2).roundToInt()
                )
            }
        }
    }
}