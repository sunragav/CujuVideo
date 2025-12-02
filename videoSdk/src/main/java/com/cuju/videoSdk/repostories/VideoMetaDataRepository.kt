package com.cuju.videoSdk.repostories

import androidx.paging.PagingSource
import com.cuju.videoSdk.db.entities.VideoMetaData
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import kotlinx.coroutines.flow.Flow

interface VideoMetaDataRepository {
    suspend fun upsertVideoMetaData(videoMetaData: VideoMetaData)
    suspend fun updateLifeCycleData(videoUri: String, videoLifeCycle: VideoLifeCycle)
    suspend fun updateWorkerId(videoUri: String, workedId: String)
    suspend fun insertVideoMetaDataOrIgnore(videoMetaDataList: List<VideoMetaData>)
    suspend fun deleteVideoMetaData(videoUri: String)
    suspend fun deleteAllVideoMetaDataNotInTheList(videoUris: List<String>)
    suspend fun getAllVideoMetaData(): List<VideoMetaData>
    suspend fun getVideoMetaDataByUri(videoUri: String): VideoMetaData?
    fun getLifeCycleState(uri: String): Flow<VideoLifeCycle>
    fun getWorkerId(uri: String): Flow<String?>
    fun getAllPaged(): PagingSource<Int, VideoMetaData>
}
