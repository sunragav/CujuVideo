package com.cuju.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.usecases.GetAllVideoMetaDataPaged
import kotlinx.coroutines.flow.Flow

class CujuGalleryViewModel(
    getAllVideoMetaDataPaged: GetAllVideoMetaDataPaged
) : ViewModel() {
    val videoMetaDataList: Flow<PagingData<VideoMetaData>> =
        getAllVideoMetaDataPaged().cachedIn(viewModelScope)
}
