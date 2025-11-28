package com.cuju.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.theme.defaultScrollBarStyle
import io.github.oikvpqya.compose.fastscroller.ScrollbarAdapter
import io.github.oikvpqya.compose.fastscroller.VerticalScrollbar

@Composable
fun CjVerticalScrollbar(
    modifier: Modifier = Modifier.Companion,
    adapter: ScrollbarAdapter,
    enablePressToScroll: Boolean = false
) {
    VerticalScrollbar(
        modifier = modifier,
        adapter = adapter,
        style = defaultScrollBarStyle(),
        enablePressToScroll = enablePressToScroll,
    )
}
