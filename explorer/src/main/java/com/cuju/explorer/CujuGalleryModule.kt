package com.cuju.explorer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.cuju.videoSdk.di.videoMetaDataModule
import com.cuju.videoSdk.domain.models.VideoMetaData
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class CujuGalleryModule {
    private val koinApplication = koinApplication {
        modules(
            videoMetaDataModule() +
                    module {
                        viewModelOf(::CujuGalleryViewModel)
                    }
        )
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Composable
    fun CujuGalleryHomeScreen(onItemClick: (VideoMetaData) -> Unit, onBackClick: () -> Unit) =
        CujuGalleryHomeScreen(
            koinApplication = koinApplication,
            onItemClick = onItemClick,
            onBackClick = onBackClick
        )
}
