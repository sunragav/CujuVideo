package com.cuju.explorer

import androidx.compose.runtime.Composable
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class CujuGallery {
    private val koinApplication = koinApplication {
        modules(
            module {
                viewModelOf(::CujuGalleryViewModel)
            }
        )
    }

    @Composable
    fun CujuExplorerScreen() = CujuGalleryScreen(koinApplication = koinApplication)
}
