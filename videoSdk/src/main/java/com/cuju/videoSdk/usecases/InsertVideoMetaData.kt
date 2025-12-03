package com.cuju.videoSdk.usecases

import com.cuju.core.getFormattedTimeStamp
import com.cuju.videoSdk.domain.models.CUJU_DATE_FORMAT
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.domain.models.VideoMetaData
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
                    timeStamp = getFormattedTimeStamp(timeStamp, CUJU_DATE_FORMAT),
                    fileName = fileName,
                    lifeCycleState = VideoLifeCycle.RECORDED
                )
            )
        }
    }
}
