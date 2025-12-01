package com.cuju.videoSdk.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.domain.models.VideoMetaData
import tech.mappie.api.ObjectMappie
import java.nio.file.Path
import kotlin.io.path.absolutePathString

object VideoMetaDataEntityToDomainMapper :
    ObjectMappie<com.cuju.videoSdk.db.entities.VideoMetaData, VideoMetaData>() {
    override fun map(from: com.cuju.videoSdk.db.entities.VideoMetaData): VideoMetaData = mapping {
        to::lifeCycleState fromValue VideoLifeCycle.valueOf(from.lifeCycleState)
    }
}

object VideoMetaDataDomainToEntityMapper :
    ObjectMappie<VideoMetaData, com.cuju.videoSdk.db.entities.VideoMetaData>() {
    override fun map(from: VideoMetaData): com.cuju.videoSdk.db.entities.VideoMetaData = mapping {
        to::lifeCycleState fromValue from.lifeCycleState.name
    }
}

object PathMapper :
    ObjectMappie<Path, VideoMetaData>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun map(from: Path): VideoMetaData = mapping {
        to::videoUri fromValue from.absolutePathString()
        to::thumbNailUri fromValue from.absolutePathString().substringBeforeLast(".") + ".png"
        to::fileName fromValue from.fileName.toString()
        to::timeStamp fromValue from.fileName.toString().substringAfter("-")
            .substringBeforeLast(".")
        to::lifeCycleState fromValue VideoLifeCycle.RECORDED
    }
}
