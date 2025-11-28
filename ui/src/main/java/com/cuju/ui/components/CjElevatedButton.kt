package com.cuju.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cuju.ui.Elevation
import com.cuju.ui.SmallMargin

@Composable
fun CjElevatedButton(onClick: () -> Unit, content: @Composable ColumnScope.() -> Unit) =
    CjButtonSmallRounded(
        elevation = ButtonDefaults.buttonElevation(defaultElevation = Elevation),
        onClick = onClick
    ) {
        Column(Modifier.padding(SmallMargin), content = content)
    }
