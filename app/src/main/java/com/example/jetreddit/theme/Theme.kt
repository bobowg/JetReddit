package com.example.jetreddit.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = RwPrimary,
    primaryVariant = RwPrimaryDark,
    onPrimary = Color.Gray,
    secondary = RwPrimaryDark,
    onSecondary = RwPrimary,
    error = Red800
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = RwPrimary,
    primaryVariant = RwPrimaryDark,
    onPrimary = Color.Gray,
    secondary = Color.LightGray,
    secondaryVariant = RwPrimaryDark,
    onSecondary = RwPrimary,
    error = Red800
)

@Composable
fun JetRedditTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (JetRedditThemeSettings.isInDarkTheme.value) DarkColorPalette else LightColorPalette ,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object JetRedditThemeSettings{
    var isInDarkTheme:MutableState<Boolean> = mutableStateOf(false)
}