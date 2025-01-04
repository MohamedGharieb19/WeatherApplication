package com.gharieb.core.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val LightColorPalette = lightColors(
    primary = Color(0xFF4FC3F7),
    primaryVariant = Color(0xFF039BE5),
    secondary = Color(0xFFFFD54F),
    background = Color(0xFFD9D9D9),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color(0xFF212121),
    onBackground = Color(0xFF212121),
    onSurface = Color(0xFF212121)
)

val DarkColorPalette = darkColors(
    primary = Color(0xFF0D47A1),
    primaryVariant = Color(0xFF002171),
    secondary = Color(0xFFFF9800),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color(0xFFB0BEC5),
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0)
)