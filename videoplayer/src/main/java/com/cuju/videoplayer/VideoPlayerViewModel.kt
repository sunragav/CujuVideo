package com.cuju.videoplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.usecases.GetVideoLifeCycleState
import com.cuju.videoSdk.usecases.GetWorkerId
import com.cuju.videoSdk.usecases.UpdateUploadStatus
import com.cuju.videoSdk.usecases.UploadFile
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VideoPlayerViewModel(
    private val uri: String,
    private val uploadFile: UploadFile,
    private val updateUploadStatus: UpdateUploadStatus,
    getVideoLifeCycleState: GetVideoLifeCycleState,
    getWorkerId: GetWorkerId
) : ViewModel() {
    val lifeCycleState: StateFlow<VideoLifeCycle> =
        getVideoLifeCycleState(uri).stateIn(viewModelScope, Lazily, VideoLifeCycle.RECORDED)

    init {
        viewModelScope.launch {
            getWorkerId(uri).collect { uuid ->
                uuid?.let { updateUploadStatus(it, uri) }
            }
        }
    }

    fun upload() = viewModelScope.launch {
        uploadFile(uri)
    }
}
