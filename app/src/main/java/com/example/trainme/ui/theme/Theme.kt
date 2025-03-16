package com.example.trainme.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define colors from your colors.xml
val Primary = Color(0xFFE6BD63) // primary
val PrimaryDark = Color(0xFFC6A14D) // primary_dark
val PrimaryLight = Color(0xFFF8D889) // primary_light
val Accent = Color(0xFFFFCA28) // accent
val TextOnLight = Color(0xFF5D4411) // text_on_light
val TextOnDark = Color(0xFFFFFFFF) // text_on_dark
val BackgroundLight = Color(0xFFFFFBEF) // background_light
val BackgroundDark = Color(0xFF3D3319) // background_dark

// Material 3 color scheme for light theme
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnDark,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextOnLight,
    secondary = Accent,
    onSecondary = TextOnDark,
    secondaryContainer = PrimaryLight,
    onSecondaryContainer = TextOnLight,
    tertiary = Primary,
    background = BackgroundLight,
    onBackground = TextOnLight,
    surface = BackgroundLight,
    onSurface = TextOnLight,
)

// Material 3 color scheme for dark theme
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = TextOnDark,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = TextOnDark,
    secondary = Accent,
    onSecondary = TextOnDark,
    secondaryContainer = PrimaryDark,
    onSecondaryContainer = TextOnDark,
    tertiary = PrimaryLight,
    background = BackgroundDark,
    onBackground = TextOnDark,
    surface = BackgroundDark,
    onSurface = TextOnDark,
)

@Composable
fun TrainMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = (if (darkTheme) PrimaryDark else PrimaryDark).toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}