package com.cuju.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.cuju.ui.theme.CujuVideoTheme

@Composable
fun CjTitleSmall(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
fun CjTitleMedium(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun CjTitleSmallContentSecondary(
    modifier: Modifier = Modifier,
    text: String,
) {
    CjTitleSmall(
        text = text,
        color = CujuVideoTheme.colorScheme.contentSecondary,
        modifier = modifier,
    )
}

@Composable
fun CjTitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    softWrap: Boolean = true,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        softWrap = softWrap,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun CjTitleLargeOnSurface(
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    text: String,
) {
    CjTitleLarge(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = textAlign,
        modifier = modifier,
    )
}
