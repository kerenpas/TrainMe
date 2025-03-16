

// com/example/trainme/presentation/theme/Theme.kt
package com.example.trainme.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    // You can customize these colors
    primary = androidx.compose.ui.graphics.Color(0xFF006C51),
    secondary = androidx.compose.ui.graphics.Color(0xFF4D6651),
    tertiary = androidx.compose.ui.graphics.Color(0xFF10675F)
)

private val DarkColors = darkColorScheme(
    // You can customize these colors
    primary = androidx.compose.ui.graphics.Color(0xFF7FD0BD),
    secondary = androidx.compose.ui.graphics.Color(0xFFB1CFBA),
    tertiary = androidx.compose.ui.graphics.Color(0xFF8ACFC9)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}