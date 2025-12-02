package com.cuju.videoSdk.usecases

import com.cuju.videoSdk.db.entities.VideoMetaData
import com.cuju.videoSdk.models.VideoLifeCycle
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertVideoMetaData(private val videoMetaDataRepository: VideoMetaDataRepository) {
    suspend operator fun invoke(
        videoOutputFile: String,
        thumbnailFile: String,
        fileName: String,
        timeStamp: Long
    ) {
        withContext(Dispatchers.Default) {
            videoMetaDataRepository.upsertVideoMetaData(
                VideoMetaData(
                    videoUri = videoOutputFile,
                    thumbNailUri = thumbnailFile,
                    timeStamp = timeStamp,
                    fileName = fileName,
                    lifeCycleState = VideoLifeCycle.RECORDED.name
                )
            )
        }
    }
}
