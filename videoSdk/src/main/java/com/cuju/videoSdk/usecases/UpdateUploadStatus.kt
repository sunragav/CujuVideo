package com.cuju.videoSdk.usecases

import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.cuju.core.applicationContext
import com.cuju.videoSdk.domain.models.VideoLifeCycle
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UpdateUploadStatus(private val videoMetaDataRepository: VideoMetaDataRepository) {
    suspend operator fun invoke(uuid: String, uri: String) {
        suspend fun updateLifeCycleState(lifeCycle: VideoLifeCycle) {
            withContext(Dispatchers.Default) {
                videoMetaDataRepository.updateLifeCycleData(uri, lifeCycle)
            }
        }

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdFlow(UUID.fromString(uuid))
            .collect { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    println("work info state:${workInfo.state}")
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> updateLifeCycleState(
                            VideoLifeCycle.UPLOADED
                        )

                        WorkInfo.State.ENQUEUED,
                        WorkInfo.State.RUNNING -> updateLifeCycleState(
                            VideoLifeCycle.UPLOADING
                        )

                        WorkInfo.State.BLOCKED,
                        WorkInfo.State.CANCELLED,
                        WorkInfo.State.FAILED -> updateLifeCycleState(
                            VideoLifeCycle.RECORDED
                        )
                    }
                }
            }
    }
}
