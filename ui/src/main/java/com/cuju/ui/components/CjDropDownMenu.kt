package com.cuju.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.theme.CujuVideoTheme
import com.cuju.ui.theme.lowestEmphasis


@Composable
fun CjDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: List<String>,
    selectedIndex: Int?,
    onItemSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        items.forEachIndexed { index, s ->
            DropdownMenuItem(
                text = {
                    CjTitleSmall(
                        text = s,
                        color = when {
                            selectedIndex == index -> CujuVideoTheme.colorScheme.contentPrimary
                            index % 2 == 0 -> CujuVideoTheme.colorScheme.contentPrimary
                            else -> CujuVideoTheme.colorScheme.contentPrimary
                        },
                        modifier = Modifier.background(
                            color = when {
                                selectedIndex == index -> CujuVideoTheme.colorScheme.backgroundAccent
                                index % 2 == 0 -> CujuVideoTheme.colorScheme.backgroundPrimary
                                else -> CujuVideoTheme.colorScheme.backgroundInverseSecondary.lowestEmphasis
                            }
                        )
                    )
                },
                onClick = {
                    onItemSelected(index)
                },
                enabled = true,
            )
        }
    }
}
