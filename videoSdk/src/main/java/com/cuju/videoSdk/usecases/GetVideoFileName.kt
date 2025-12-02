package com.cuju.videoSdk.usecases

import com.cuju.core.getFormattedTimeStamp
import com.cuju.videoSdk.domain.models.CUJU_DATE_FORMAT
import com.cuju.videoSdk.domain.models.CUJU_FILE_NAME
import com.cuju.videoSdk.domain.models.CUJU_VIDEO_FORMAT

class GetVideoFileName() {
    operator fun invoke(timeMillis: Long): String {
        val timeStamp = getFormattedTimeStamp(timeMillis, CUJU_DATE_FORMAT)
        return "$CUJU_FILE_NAME-$timeStamp.$CUJU_VIDEO_FORMAT"
    }
}
