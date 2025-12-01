package com.cuju.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cuju.ui.theme.CujuVideoTheme


@Composable
fun CjLabelMedium(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
    )
}

@Composable
fun CjLabelLarge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Composable
fun CjLabelXLarge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = CujuVideoTheme.typography.bodyXLarge,
    )
}

@Composable
fun CjLabelLargeOnSurface(
    modifier: Modifier = Modifier,
    text: String,
) {
    CjLabelLarge(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun CjLabelMediumSecondary(
    modifier: Modifier = Modifier,
    text: String,
) {
    CjLabelMedium(
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
    )
}

@Composable
fun CjLabelMediumContentTertiary(
    modifier: Modifier = Modifier,
    text: String,
) {
    CjLabelMedium(
        text = text,
        color = CujuVideoTheme.colorScheme.contentTertiary,
        modifier = modifier,
    )
}

@Composable
fun CjLabelSmall(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
    )
}
