package com.cuju.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.DoubleMargin
import com.cuju.ui.theme.CujuVideoTheme

@Composable
fun CjCircleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = CujuVideoTheme.colorScheme.contentNegative),
        modifier = Modifier.size(DoubleMargin),
    ) {}
}
