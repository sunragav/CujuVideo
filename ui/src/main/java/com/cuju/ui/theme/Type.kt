package com.cuju.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CujuTypography(
    val headlineXSmall: TextStyle,
    val bodyXLarge: TextStyle,
    val bodyMedium: TextStyle
)

val LocalCujuTypography = staticCompositionLocalOf {
    CujuTypography(
        headlineXSmall = TextStyle.Default,
        bodyXLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default
    )
}

val robotoCondensedFamily = FontFamily(
    Font(DeviceFontFamilyName("sans-serif-condensed"), FontWeight.Normal)
)

@Composable
fun CujuTypography() = CujuTypography(
    headlineXSmall = TextStyle(
        fontFamily = robotoCondensedFamily,
        fontSize = 20.0.sp,
        letterSpacing = 0.0.sp,
        lineHeight = 28.0.sp,
    ),
    bodyXLarge = TextStyle(
        fontFamily = robotoCondensedFamily,
        fontSize = 18.0.sp,
        letterSpacing = 0.0.sp,
        lineHeight = 28.0.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoCondensedFamily,
        fontSize = 16.0.sp,
        letterSpacing = 0.0.sp,
        lineHeight = 20.0.sp,
    )
)
