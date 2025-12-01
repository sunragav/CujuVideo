package com.cuju.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.usecases.GetAllVideoMetaDataPaged
import com.cuju.videoSdk.usecases.PopulateVideoMetaDataDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CujuGalleryViewModel(
    populateVideoMetaDataDb: PopulateVideoMetaDataDb,
    getAllVideoMetaDataPaged: GetAllVideoMetaDataPaged
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.Default) {
            populateVideoMetaDataDb()
        }
    }

    val videoMetaDataList: Flow<PagingData<VideoMetaData>> =
        getAllVideoMetaDataPaged().cachedIn(viewModelScope)
}
