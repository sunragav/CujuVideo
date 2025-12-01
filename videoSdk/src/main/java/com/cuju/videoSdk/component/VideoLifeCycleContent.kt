package com.cuju.videoSdk.component

import androidx.compose.runtime.Composable
import com.cuju.ui.components.CjLabelLarge
import com.cuju.ui.theme.CujuVideoTheme
import com.cuju.videoSdk.domain.models.VideoLifeCycle

@Composable
fun VideoLifeCycleContent(state: VideoLifeCycle) {
    when (state) {
        VideoLifeCycle.RECORDED -> CjLabelLarge(
            text = state.name,
            color = CujuVideoTheme.colorScheme.contentAccent
        )

        VideoLifeCycle.UPLOADING -> CjLabelLarge(
            text = state.name,
            color = CujuVideoTheme.colorScheme.contentNegative
        )

        VideoLifeCycle.UPLOADED -> CjLabelLarge(
            text = state.name,
            color = CujuVideoTheme.colorScheme.allGood
        )
    }
}
