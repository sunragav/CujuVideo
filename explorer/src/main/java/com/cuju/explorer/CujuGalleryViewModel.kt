package com.cuju.explorer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuju.core.listDirectoryEntriesFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.nio.file.Path

private const val MP4_FILE_GLOB = "*.mp4"

class CujuGalleryViewModel(path: String) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    val files: StateFlow<List<Path>> = Path.of(path).listDirectoryEntriesFlow(MP4_FILE_GLOB)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}
