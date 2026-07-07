package com.mo.assistant.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MOdarkColorScheme = darkColorScheme(
    primary = MOPurple,
    onPrimary = MOBgDark,
    primaryContainer = MOPurpleDeep,
    onPrimaryContainer = MOTextPrimary,
    secondary = MOAccentGreen,
    onSecondary = MOBgDark,
    tertiary = MOBlue,
    background = MOBgDark,
    onBackground = MOTextPrimary,
    surface = MOSurface,
    onSurface = MOTextPrimary,
    surfaceVariant = MOSurface2,
    onSurfaceVariant = MOTextSecondary,
    error = MODanger,
    onError = MOTextPrimary
)

private val MOlightColorScheme = lightColorScheme(
    primary = MOPurpleDeep,
    onPrimary = MOTextPrimary,
    primaryContainer = MOPurpleLight,
    secondary = MOAccentGreenDeep,
    background = MOBgLight,
    surface = MOSurfaceLight,
    onBackground = MOBgDark,
    onSurface = MOBgDark
)

@Composable
fun MOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MOdarkColorScheme
        else -> MOlightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MOTypography,
        content = content
    )
}