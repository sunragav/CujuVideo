package com.cuju.videoSdk.domain.models

import kotlinx.serialization.Serializable

@Serializable
enum class VideoLifeCycle {
    RECORDED,
    UPLOADING,
    UPLOADED,
}
