package com.example.presentation_common.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val DefaultColorScheme = lightColorScheme(
    primary = DodgerBlue100,
    onPrimary = Pumpkin,
    secondary = White100,
    onSecondary = Dark,
    background = GhostWhite,
    onBackground = Gray,
    surface = GhostWhite,
    onSurface = Gray,
    error = Red,
    onError = GhostWhite,
    errorContainer = DodgerBlue100,
    onErrorContainer = GhostWhite,
    outline = DodgerBlue100,
    inverseSurface = Turquoise,
    inverseOnSurface = BlueJeans,
    surfaceVariant = White60,
    surfaceContainerLow = DodgerBlue20
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