package com.cuju.videoSdk.usecases

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.cuju.core.applicationContext
import com.cuju.videoSdk.models.VideoLifeCycle
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import com.cuju.videoSdk.workmanager.UploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UploadFile(private val videoMetaDataRepository: VideoMetaDataRepository) {
    suspend operator fun invoke(uri: String) {
        val metadata = videoMetaDataRepository.getVideoMetaDataByUri(uri)
        val uuid = UUID.randomUUID()
        val powerConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<UploadWorker>().setConstraints(powerConstraints)
                .setInputData(
                    Data.Builder().putString(UploadWorker.FILE_URI_TO_UPLOAD, uri).build()
                )
                .setId(uuid)
                .build()
        )

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdFlow(uuid)
            .collect { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.UPLOADED
                        )

                        WorkInfo.State.ENQUEUED,
                        WorkInfo.State.RUNNING -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.UPLOADING
                        )

                        WorkInfo.State.BLOCKED,
                        WorkInfo.State.CANCELLED,
                        WorkInfo.State.FAILED -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.RECORDED
                        )
                    }
                }
            }
    }

    private suspend fun updateLifeCycleState(
        metadata: com.cuju.videoSdk.db.entities.VideoMetaData?,
        lifeCycleState: VideoLifeCycle
    ) {
        withContext(Dispatchers.Default) {
            metadata?.let { md ->
                videoMetaDataRepository.upsertVideoMetaData(md.copy(lifeCycleState = lifeCycleState.name))
            }
        }
    }
}
