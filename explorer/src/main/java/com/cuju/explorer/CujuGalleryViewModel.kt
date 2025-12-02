package com.cuju.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.usecases.GetAllVideoMetaDataPaged
import com.cuju.videoSdk.usecases.GetWorkerId
import com.cuju.videoSdk.usecases.UpdateUploadStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CujuGalleryViewModel(
    private val getWorkerId: GetWorkerId,
    private val updateUploadStatus: UpdateUploadStatus,
    getAllVideoMetaDataPaged: GetAllVideoMetaDataPaged,
) : ViewModel() {
    val videoMetaDataList: Flow<PagingData<VideoMetaData>> =
        getAllVideoMetaDataPaged().cachedIn(viewModelScope)

    fun updateUploadStatus(uri: String) = viewModelScope.launch {
        getWorkerId(uri).collect { uuid ->
            uuid?.let { updateUploadStatus(it, uri) }
        }
    }
}
