package com.ontest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val OnTestColorScheme = lightColorScheme(
    primary = VioletPrimary,
    onPrimary = White,
    primaryContainer = VioletLight,
    onPrimaryContainer = TextPrimary,
    secondary = CamelBrown,
    onSecondary = White,
    secondaryContainer = CamelLight,
    onSecondaryContainer = TextPrimary,
    tertiary = CamelDark,
    onTertiary = White,
    tertiaryContainer = CamelSurface,
    onTertiaryContainer = TextPrimary,
    background = White,
    onBackground = TextPrimary,
    surface = White,
    onSurface = TextPrimary,
    surfaceVariant = OffWhite,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor,
    outlineVariant = ShimmerColor,
    error = UrsRed,
    onError = White,
    errorContainer = Color(0xFFFDE8E8),
    onErrorContainer = TextPrimary,
    inverseSurface = TextPrimary,
    inverseOnSurface = White,
    surfaceTint = VioletPrimary
)

@Composable
fun OnTestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OnTestColorScheme,
        typography = OnTestTypography,
        shapes = OnTestShapes,
        content = content
    )
}
