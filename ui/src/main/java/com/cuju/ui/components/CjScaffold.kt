package com.cuju.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.SmallMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CjScaffold(title: String, onBackClick: () -> Unit = {}, content: @Composable () -> Unit) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            CjMediumTopAppBar(
                title = {
                    CjTitleLarge(modifier = Modifier.padding(start = SmallMargin), text = title)
                },
                onNavigateUp = onBackClick
            )
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues = paddingValues)) {
            content()
        }
    }
}
