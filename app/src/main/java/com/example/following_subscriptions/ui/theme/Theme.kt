package com.example.following_subscriptions.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.following_subscriptions.R
private val DarkColorScheme = darkColorScheme(
    primary = CreamWhite,
    background = DeepBlue,
    surface = DeepBlue,
    onPrimary = DeepBlue,
    onBackground = CreamWhite,
    onSurface = CreamWhite
)

@Composable
fun Following_subscriptionsTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val DuricFont= FontFamily(
    Font(R.font.duric_font)
)

val LletreFont = FontFamily(
    Font(R.font.lletraferida_font)
)