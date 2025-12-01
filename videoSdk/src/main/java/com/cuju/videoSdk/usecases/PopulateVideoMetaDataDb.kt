package com.cuju.videoSdk.usecases

import com.cuju.videoSdk.mappers.VideoMetaDataDomainToEntityMapper
import com.cuju.videoSdk.repostories.VideoMetaDataRepository

class PopulateVideoMetaDataDb(
    private val getVideoMetaDataListFromTheAppDirectory: GetVideoMetaDataListFromTheAppDirectory,
    private val videoMetaDataRepository: VideoMetaDataRepository
) {
    suspend operator fun invoke() =
        getVideoMetaDataListFromTheAppDirectory().collect { videoMetaData ->
            videoMetaDataRepository.deleteAllVideoMetaDataNotInTheList(videoMetaData.map { it.videoUri })
            videoMetaDataRepository.insertVideoMetaDataOrIgnore(
                videoMetaData.map(
                    VideoMetaDataDomainToEntityMapper::map
                )
            )
        }
}
