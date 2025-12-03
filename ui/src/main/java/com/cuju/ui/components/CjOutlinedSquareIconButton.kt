package com.cuju.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cuju.ui.LargeMargin
import com.cuju.ui.theme.CujuVideoTheme

@Composable
fun CjOutlinedSquareIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    icon: ImageVector
) {
    OutlinedIconButton(
        modifier = modifier.size(LargeMargin),
        enabled = enabled,
        shape = CjFilledTonalSquareIconButtonDefaults.shape(),
        border = BorderStroke(2.dp, CujuVideoTheme.colorScheme.borderTransparent),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

object CjFilledTonalSquareIconButtonDefaults {
    @Composable
    fun shape() = MaterialTheme.shapes.small
}
