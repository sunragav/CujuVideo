package com.cuju.videoplayer

import androidx.compose.runtime.Composable
import com.cuju.videoSdk.di.videoMetaDataModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class CujuVideoPlayerModule {
    private val koinApplication = koinApplication {
        modules(
            videoMetaDataModule() +
                    module {
                        viewModelOf(::VideoPlayerViewModel)
                    }
        )
    }

    @Composable
    fun CujuVideoPlayerScreen(uri: String, onBackClick: () -> Unit) =
        VideoPlayerHomeScreen(koinApplication, uri, onBackClick)
}
