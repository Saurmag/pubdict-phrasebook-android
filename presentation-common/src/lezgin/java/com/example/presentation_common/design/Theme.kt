package com.example.presentation_common.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val DefaultColorScheme = lightColorScheme(
    primary = Green100,
    onPrimary = Magenta,
    secondary = White100,
    onSecondary = Dark,
    background = GhostWhite,
    onBackground = Gray,
    surface = GhostWhite,
    onSurface = Gray,
    error = Red,
    onError = GhostWhite,
    errorContainer = Green100,
    onErrorContainer = GhostWhite,
    outline = Green100,
    inverseSurface = SpringGreen,
    inverseOnSurface = UFOGreen,
    surfaceVariant = White60,
    surfaceContainerLow = Green20
)

@Composable
fun PubDictTheme(
    content: @Composable () -> Unit
) {
    val defaultGradientColors = GradientColors(
        top = DefaultColorScheme.inverseSurface,
        bottom = DefaultColorScheme.inverseOnSurface
    )
    val tintColor = TintTheme(iconTint = Dark)
    CompositionLocalProvider(
        LocalGradientColors provides defaultGradientColors,
        LocalTintTheme provides tintColor
    ) {
        MaterialTheme(colorScheme = DefaultColorScheme, typography = PubDictTypography, content = content)
    }
}