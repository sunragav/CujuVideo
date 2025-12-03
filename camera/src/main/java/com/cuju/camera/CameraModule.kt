package com.cuju.camera

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.cuju.videoSdk.di.videoMetaDataModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class CameraModule {
    private val koinApplication = koinApplication {
        modules(
            videoMetaDataModule() +
                    module {
                        viewModelOf(::CameraViewModel)
                    }
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CujuCameraHomeScreen(onBackClick: () -> Unit) =
        CameraHomeScreen(koinApplication = koinApplication, onBackClick = onBackClick)
}
