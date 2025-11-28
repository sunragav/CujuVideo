package com.cuju.ui.components

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.cuju.ui.theme.CujuVideoTheme
import com.cuju.ui.theme.opacity25

object CjButtonDefaults {
    @Composable
    fun buttonColors() = ButtonDefaults.buttonColors().copy(
        containerColor = CujuVideoTheme.colorScheme.backgroundAccent,
        contentColor = CujuVideoTheme.colorScheme.contentPrimary,
        disabledContainerColor = CujuVideoTheme.colorScheme.backgroundStateDisabled,
        disabledContentColor = CujuVideoTheme.colorScheme.contentStateDisabled,
    )

    @Composable
    fun filledTonalButtonColors() = ButtonDefaults.filledTonalButtonColors().copy(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
    )

    @Composable
    fun filledTonalButtonPrimaryColors() = ButtonDefaults.filledTonalButtonColors().copy(
        containerColor = CujuVideoTheme.colorScheme.backgroundPrimary,
        contentColor = CujuVideoTheme.colorScheme.contentPrimary,
    )

    @Composable
    fun semiTransparentButtonColors() = buttonColors().copy(
        containerColor = CujuVideoTheme.colorScheme.backgroundInversePrimary.opacity25,
        contentColor = CujuVideoTheme.colorScheme.contentInversePrimary,
        disabledContainerColor = CujuVideoTheme.colorScheme.backgroundInversePrimary.opacity25,
        disabledContentColor = CujuVideoTheme.colorScheme.contentStateDisabled,
    )

    @Composable
    fun shape() = MaterialTheme.shapes.small
}
