package com.cuju.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CujuVideoTheme(
    useCujuMaterialTheme: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme) {
            lightCujuColorScheme()
        } else {
            darkCujuColorScheme()
        }
    val typography: CujuTypography = CujuTypography()

    CompositionLocalProvider(
        LocalCujuColorScheme provides colorScheme,
        LocalCujuTypography provides typography,
    ) {
        if (useCujuMaterialTheme) {
            CujuMaterialTheme(content = content)
        } else {
            content()
        }
    }
}

object CujuVideoTheme {
    val colorScheme: CujuColorScheme
        @Composable
        get() = LocalCujuColorScheme.current

    val typography: CujuTypography
        @Composable
        get() = LocalCujuTypography.current
}

@Composable
fun CujuMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme) {
            lightCujuMaterialColorScheme
        } else {
            darkCujuMaterialColorScheme
        }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
