package com.cuju.videoSdk.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class VideoMetaData(
    val videoUri: String,
    val thumbNailUri: String,
    val fileName: String,
    val timeStamp: String,
    val lifeCycleState: VideoLifeCycle
)
