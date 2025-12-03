package com.cuju.videoSdk.repostories

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.cuju.videoSdk.db.VideoMetaDataDb
import com.cuju.videoSdk.db.dao.VideoMetaDataDao
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.mappers.VideoMetaDataDomainToEntityMapper
import com.cuju.videoSdk.mappers.VideoMetaDataEntityToDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalVideoMetaDataRepository(
    private val videoMetaDataDb: VideoMetaDataDb,
    private val videoMetaDataDao: VideoMetaDataDao
) :
    VideoMetaDataRepository {
    override suspend fun upsertVideoMetaData(videoMetaData: VideoMetaData) {
        videoMetaDataDb.withTransaction {
            videoMetaDataDao.upsert(VideoMetaDataDomainToEntityMapper.map(videoMetaData))
        }
    }

    override suspend fun updateLifeCycleData(videoUri: String, videoLifeCycle: VideoLifeCycle) {
        videoMetaDataDb.withTransaction {
            videoMetaDataDao.updateLifeCycleData(videoUri, videoLifeCycle.name)
        }
    }

    override suspend fun updateWorkerId(videoUri: String, workedId: String) {
        videoMetaDataDb.withTransaction {
            videoMetaDataDao.updateWorkerId(videoUri, workedId)
        }
    }

    override suspend fun insertVideoMetaDataOrIgnore(videoMetaDataList: List<VideoMetaData>) {
        videoMetaDataDao.insertOrIgnore(videoMetaDataList.map(VideoMetaDataDomainToEntityMapper::map))
    }

    override suspend fun deleteVideoMetaData(videoUri: String) {
        val videoMetaData = getVideoMetaDataByUri(videoUri)
        videoMetaData?.let {
            val videoMetaDataEntity = VideoMetaDataDomainToEntityMapper.map(it)
            videoMetaDataDao.delete(videoMetaDataEntity)
        }
    }

    override suspend fun deleteAllVideoMetaDataNotInTheList(videoUris: List<String>) {
        videoMetaDataDao.deleteItemNotInIds(videoUris.toTypedArray())
    }

    override suspend fun getAllVideoMetaData(): List<VideoMetaData> =
        videoMetaDataDao.all().map(VideoMetaDataEntityToDomainMapper::map)


    override suspend fun getVideoMetaDataByUri(videoUri: String): VideoMetaData? =
        videoMetaDataDao.subsetByColumn("videoUri", videoUri)
            .map(VideoMetaDataEntityToDomainMapper::map).firstOrNull()

    override fun getLifeCycleState(uri: String): Flow<VideoLifeCycle> =
        videoMetaDataDao.getLifeCycleState(uri).map { VideoLifeCycle.valueOf(it) }

    override fun getWorkerId(uri: String): Flow<String?> =
        videoMetaDataDao.getWorkerId(uri)

    override fun getAllPaged(): PagingSource<Int, com.cuju.videoSdk.db.entities.VideoMetaData> =
        videoMetaDataDao.pagingSource()
}
