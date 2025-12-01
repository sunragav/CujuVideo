package com.cuju.videoSdk.repostories

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.cuju.videoSdk.db.VideoMetaDataDb
import com.cuju.videoSdk.db.dao.VideoMetaDataDao
import com.cuju.videoSdk.db.entities.VideoMetaData
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalVideoMetaDataRepository(
    private val videoMetaDataDb: VideoMetaDataDb,
    private val videoMetaDataDao: VideoMetaDataDao
) :
    VideoMetaDataRepository {
    override suspend fun upsertVideoMetaData(videoMetaData: VideoMetaData) {
        videoMetaDataDb.withTransaction {
            videoMetaDataDao.upsert(videoMetaData)
        }
    }

    override suspend fun insertVideoMetaDataOrIgnore(videoMetaDataList: List<VideoMetaData>) {
        videoMetaDataDao.insertOrIgnore(videoMetaDataList)
    }

    override suspend fun deleteVideoMetaData(videoUri: String) {
        val videoMetaData = getVideoMetaDataByUri(videoUri)
        videoMetaData?.let { videoMetaDataDao.delete(it) }
    }

    override suspend fun deleteAllVideoMetaDataNotInTheList(videoUris: List<String>) {
        videoMetaDataDao.deleteItemNotInIds(videoUris.toTypedArray())
    }

    override suspend fun getAllVideoMetaData(): List<VideoMetaData> =
        videoMetaDataDao.all()

    override suspend fun getVideoMetaDataByUri(videoUri: String): VideoMetaData? =
        videoMetaDataDao.subsetByColumn("videoUri", videoUri).firstOrNull()

    override fun getLifeCycleState(uri: String): Flow<VideoLifeCycle> =
        videoMetaDataDao.getLifeCycleState(uri).map { VideoLifeCycle.valueOf(it) }

    override fun getAllPaged(): PagingSource<Int, VideoMetaData> =
        videoMetaDataDao.pagingSource()


}
