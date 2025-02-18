package com.example.presentation_common.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

val DefaultColorScheme = lightColorScheme(
    primary = DarkSpringGreen100,
    onPrimary = Claret,
    secondary = White100,
    onSecondary = Dark,
    background = GhostWhite,
    onBackground = Gray,
    surface = GhostWhite,
    onSurface = Gray,
    error = Red,
    onError = GhostWhite,
    errorContainer = DarkSpringGreen100,
    onErrorContainer = GhostWhite,
    outline = DarkSpringGreen100,
    inverseSurface = ScreamingGreen,
    inverseOnSurface = Eucalyptus,
    surfaceVariant = White60,
    surfaceContainerLow = DarkSpringGreen20
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