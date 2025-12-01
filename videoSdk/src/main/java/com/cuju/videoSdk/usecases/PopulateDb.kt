package com.cuju.videoSdk.usecases

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.cuju.core.applicationContext
import com.cuju.videoSdk.workmanager.PopulateVideoMetaDataWorker
import java.util.UUID

class PopulateDb {
    operator fun invoke() {
        val uuid = UUID.randomUUID()
        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<PopulateVideoMetaDataWorker>()
                .setInputData(
                    Data.Builder().putString(
                        PopulateVideoMetaDataWorker.POPULATE_VIDEO_META_DATA_INPUT_DATA,
                        ""
                    ).build()
                ).setId(uuid)
                .build()
        )
    }
}
