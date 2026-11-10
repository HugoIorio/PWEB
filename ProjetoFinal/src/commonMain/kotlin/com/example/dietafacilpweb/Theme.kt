// Arquivo para listar as cores dos temas do aplicativo
package com.example.dietafacilpweb

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object Cores {
    val Primary = Color(0xFF006A45)
    val PrimaryContainer = Color(0xFF1B845A)
    val OnPrimaryContainer = Color(0xFFF6FFF6)
    val PrimaryFixed = Color(0xFF96F6C2)
    val PrimaryFixedDim = Color(0xFF7AD9A7)
    val OnPrimaryFixedVariant = Color(0xFF005234)
    val Secondary = Color(0xFF944A19)
    val SecondaryContainer = Color(0xFFFE9E66)
    val Background = Color(0xFFF8FAF8)
    val Surface = Color(0xFFF8FAF8)

    val SurfaceVariant = Color(0xFFFFFF)
    val SurfaceLowest = Color(0xFFFFFFFF)
    val SurfaceLow = Color(0xFFF2F4F2)
    val SurfaceHigh = Color(0xFFE7E9E7)
    val SurfaceHighest = Color(0xFFE1E3E1)
    val OnSurface = Color(0xFF191C1B)
    val OnSurfaceVariant = Color(0xFF3E4942)
    val Outline = Color(0xFF6E7A71)
    val OutlineVariant = Color(0xFFBEC9C0)
    val Error = Color(0xFFBA1A1A)
    val ErrorContainer = Color(0xFFFFDAD6)
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Cores.Primary,
            onPrimary = Color.White,
            primaryContainer = Cores.PrimaryContainer,
            onPrimaryContainer = Cores.OnPrimaryContainer,
            secondary = Cores.Secondary,
            background = Cores.Background,
            surface = Cores.Surface,
            onSurface = Cores.OnSurface,
            error = Cores.Error,
        ),
        content = content,
    )
}
