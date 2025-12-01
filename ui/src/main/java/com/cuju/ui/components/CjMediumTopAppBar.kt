package com.cuju.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.cuju.ui.SmallMargin
import com.cuju.ui.theme.CujuVideoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CjMediumTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    icon: ImageVector = Icons.Default.Close,
    onNavigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            CjOutlinedSquareIconButton(
                modifier = Modifier.padding(start = SmallMargin),
                onClick = onNavigateUp,
                icon = icon,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CujuVideoTheme.colorScheme.backgroundPrimary,
            scrolledContainerColor = CujuVideoTheme.colorScheme.backgroundPrimary
        ),
        scrollBehavior = scrollBehavior
    )
}
