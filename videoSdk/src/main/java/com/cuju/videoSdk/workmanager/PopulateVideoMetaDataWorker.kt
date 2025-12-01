package com.cuju.videoSdk.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cuju.videoSdk.usecases.PopulateVideoMetaDataDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class PopulateVideoMetaDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val populateVideoMetaDataDb: PopulateVideoMetaDataDb,
) : CoroutineWorker(context, workerParams), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.Default) {
                populateVideoMetaDataDb()
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    companion object Companion {
        const val POPULATE_VIDEO_META_DATA_SUCCESS = "POPULATE_VIDEO_META_DATA_SUCCESS"
        const val POPULATE_VIDEO_META_DATA_INPUT_DATA = "POPULATE_VIDEO_META_DATA_INPUT_DATA"
    }
}
