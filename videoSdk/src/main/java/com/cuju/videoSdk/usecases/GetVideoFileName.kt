package com.cuju.videoSdk.usecases

import com.cuju.videoSdk.domain.models.CUJU_DATE_FORMAT
import com.cuju.videoSdk.domain.models.CUJU_FILE_NAME
import com.cuju.videoSdk.domain.models.CUJU_VIDEO_FORMAT
import java.text.SimpleDateFormat
import java.util.Locale

class GetVideoFileName() {
    operator fun invoke(timeMillis: Long): String {
        val timeStamp = SimpleDateFormat(CUJU_DATE_FORMAT, Locale.US)
            .format(timeMillis)
        return "$CUJU_FILE_NAME-$timeStamp.$CUJU_VIDEO_FORMAT"
    }
}
