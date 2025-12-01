package com.cuju.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.CornerRadius
import com.cuju.ui.LargeMargin
import com.cuju.ui.theme.CujuVideoTheme

@Composable
fun CjSquareButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(CornerRadius),
        colors = ButtonDefaults.buttonColors(containerColor = CujuVideoTheme.colorScheme.contentNegative),
        modifier = Modifier.size(LargeMargin),
    ) {}
}
