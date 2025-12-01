package com.cuju.videoplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuju.ui.Margin
import com.cuju.ui.components.CjLargeButton
import com.cuju.ui.components.CjScaffold
import com.cuju.video.R
import com.cuju.videoSdk.component.VideoLifeCycleContent
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf

@Composable
fun VideoPlayerHomeScreen(koinApplication: KoinApplication, uri: String, onBackClick: () -> Unit) {
    KoinIsolatedContext(context = koinApplication) {
        CjScaffold(
            title = stringResource(R.string.video_player_home_screen_playing_title, uri),
            onBackClick = onBackClick
        ) {
            val viewModel = koinViewModel<VideoPlayerViewModel> {
                parametersOf(uri)
            }
            val state by viewModel.lifeCycleState.collectAsStateWithLifecycle()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(Margin),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(Margin),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    VideoLifeCycleContent(state = state)
                    CjLargeButton(
                        enabled = state == VideoLifeCycle.RECORDED,
                        onClick = viewModel::upload,
                        text = stringResource(R.string.video_player_screen_upload_action)
                    )
                }
                VideoPlayerContent(uri = uri)
            }
        }
    }
}
