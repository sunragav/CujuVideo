@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.cuju.videoplayer

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    uri: String,
    modifier: Modifier = Modifier,
) {
    var player by remember { mutableStateOf<Player?>(null) }
    val context = LocalContext.current
    PlayerLifecycle(
        initialize = {
            player = ExoPlayer.Builder(context)
                .build().apply {
                    setMediaItem(MediaItem.fromUri(uri))
                    prepare()
                    playWhenReady = true
                }

            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                }
            })
        },
        release = {
            player?.release()
        }
    )
    // When in preview, early return a Box with the received modifier preserving layout
    if (LocalInspectionMode.current) {
        Box(modifier = modifier)
        return
    }

    AndroidView(
        factory = { PlayerView(it) },
        update = { playerView ->
            playerView.player = player
        },
        modifier = Modifier
            .focusable(),
    )
}

/**
 * Handle the lifecycle of the player, making sure it's initialized and released at the
 * right moments in the Android lifecycle.
 */
@Composable
private fun PlayerLifecycle(
    initialize: () -> Unit,
    release: () -> Unit,
) {
    val currentOnInitializePlayer by rememberUpdatedState(initialize)
    val currentOnReleasePlayer by rememberUpdatedState(release)

    /**
     * Android API level 24 and higher supports multiple windows. As your app can be visible, but
     * not active in split window mode, you need to initialize the player in onStart
     */
    LifecycleStartEffect(true) {
        currentOnInitializePlayer()
        onStopOrDispose {
            currentOnReleasePlayer()
        }
    }
}
