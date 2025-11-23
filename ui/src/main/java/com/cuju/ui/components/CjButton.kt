package com.cuju.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.cuju.ui.ButtonSmallContentPaddingHorizontal
import com.cuju.ui.ButtonSmallHeight
import com.cuju.ui.ButtonSmallPaddingVertical
import com.cuju.ui.ButtonSmallShapeCornerSize


@Composable
fun CjLargeButton(
    modifier: Modifier = Modifier,
    shape: Shape = CjButtonDefaults.shape(),
    colors: ButtonColors = CjButtonDefaults.buttonColors(),
    text: String? = null,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = { text?.let { Text(text = text) } }
) {
    Button(
        modifier = modifier, colors = colors,
        shape = shape,
        enabled = enabled,
        contentPadding = contentPadding,
        onClick = onClick,
        content = content
    )
}

@Composable
fun CjButtonSmallRounded(
    modifier: Modifier = Modifier,
    colors: ButtonColors = CjButtonDefaults.filledTonalButtonColors(),
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .heightIn(min = ButtonSmallHeight)
            .padding(vertical = ButtonSmallPaddingVertical),
        colors = colors,
        shape = RoundedCornerShape(size = ButtonSmallShapeCornerSize),
        contentPadding = PaddingValues(horizontal = ButtonSmallContentPaddingHorizontal),
        enabled = enabled,
        onClick = onClick,
    ) {
        CjTitleSmall(text = text)
    }
}
