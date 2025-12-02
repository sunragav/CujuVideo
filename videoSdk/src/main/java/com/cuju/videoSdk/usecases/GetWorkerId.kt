package com.cuju.videoSdk.usecases

import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import kotlinx.coroutines.flow.Flow

class GetWorkerId(private val videoMetaDataRepository: VideoMetaDataRepository) {
    operator fun invoke(uri: String): Flow<String?> {
        return videoMetaDataRepository.getWorkerId(uri)
    }
}
