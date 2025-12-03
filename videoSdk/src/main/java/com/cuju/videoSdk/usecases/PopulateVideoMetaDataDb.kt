package com.cuju.videoSdk.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.cuju.videoSdk.repostories.VideoMetaDataRepository

class PopulateVideoMetaDataDb(
    private val getVideoMetaDataListFromTheAppDirectory: GetVideoMetaDataListFromTheAppDirectory,
    private val videoMetaDataRepository: VideoMetaDataRepository
) {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend operator fun invoke() =
        getVideoMetaDataListFromTheAppDirectory().collect { videoMetaData ->
            videoMetaDataRepository.deleteAllVideoMetaDataNotInTheList(videoMetaData.map { it.videoUri })
            videoMetaDataRepository.insertVideoMetaDataOrIgnore(videoMetaData)
        }
}
