package com.cuju.videoSdk.usecases

import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import kotlinx.coroutines.flow.Flow

class GetVideoLifeCycleState(private val videoMetaDataRepository: VideoMetaDataRepository) {
    operator fun invoke(uri: String): Flow<VideoLifeCycle> {
        return videoMetaDataRepository.getLifeCycleState(uri)
    }
}
