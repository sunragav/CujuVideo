package com.cuju.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.oikvpqya.compose.fastscroller.rememberScrollbarAdapter

@Composable
fun CjContentWithFooterAndScrollBar(
    modifier: Modifier = Modifier,
    innerContentPaddingValues: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
    footer: @Composable ColumnScope.() -> Unit = {}
) {
    val state = rememberScrollState()
    Box(modifier) {
        Column(
            Modifier
                .padding(innerContentPaddingValues)
                .fillMaxHeight()
                .verticalScroll(state)
        ) {
            content()
            Spacer(modifier = Modifier.weight(1f))
            footer()
        }
        CjVerticalScrollbar(
            modifier = Modifier
                .align(Alignment.TopEnd),
            adapter = rememberScrollbarAdapter(scrollState = state)
        )
    }
}
