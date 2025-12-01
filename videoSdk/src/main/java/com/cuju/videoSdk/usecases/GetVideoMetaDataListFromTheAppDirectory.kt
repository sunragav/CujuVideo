package com.cuju.videoSdk.usecases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.cuju.core.listDirectoryEntriesFlow
import com.cuju.videoSdk.domain.models.CUJU_VIDEO_FORMAT
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.mappers.PathMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.file.Path

class GetVideoMetaDataListFromTheAppDirectory(
    private val context: Context,
) {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    operator fun invoke(): Flow<List<VideoMetaData>> {

        val path = context.filesDir.absolutePath
        return Path.of(path).listDirectoryEntriesFlow("*.$CUJU_VIDEO_FORMAT").map {
            it.map(PathMapper::map)
        }
    }
}
