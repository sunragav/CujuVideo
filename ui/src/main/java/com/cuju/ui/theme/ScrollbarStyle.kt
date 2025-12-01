package com.cuju.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.cuju.ui.CornerRadius
import io.github.oikvpqya.compose.fastscroller.ThumbStyle
import io.github.oikvpqya.compose.fastscroller.material.defaultMaterialScrollbarStyle

@Composable
fun defaultScrollBarStyle() = defaultMaterialScrollbarStyle().copy(
    thumbStyle = ThumbStyle(
        shape = RoundedCornerShape(CornerRadius),
        unhoverColor = Color(CujuVideoTheme.colorScheme.contentPrimary.toArgb()),
        hoverColor = Color(CujuVideoTheme.colorScheme.contentPrimary.toArgb()),
    )
)
