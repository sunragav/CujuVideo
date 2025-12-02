package com.cuju.videoSdk.usecases

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.cuju.core.applicationContext
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import com.cuju.videoSdk.workmanager.UploadWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UploadFile(private val videoMetaDataRepository: VideoMetaDataRepository) {
    suspend operator fun invoke(uri: String) {
        val uuid = UUID.randomUUID()
        withContext(Dispatchers.Default) {
            videoMetaDataRepository.updateWorkerId(uri, uuid.toString())
        }
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
    }
}
