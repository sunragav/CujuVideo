package com.cuju.videoSdk.workmanager

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cuju.core.applicationContext
import com.cuju.video.R
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent

class UploadWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    val uri = requireNotNull(inputData.getString(FILE_URI_TO_UPLOAD))// 1
    override suspend fun doWork(): Result = runCatching {
        uploadVideo(uri) // 2
        Result.success() // 3
    }.getOrElse {
        return@getOrElse when (it) { // 4
            is UploadingFailedException.Failed -> {
                sendNotification(
                    context.getString(
                        R.string.notification_error,
                        it.message
                    )
                )
                Result.retry() // 5
            }

            is UploadingFailedException.FileNotFound -> {
                sendNotification(
                    context.getString(
                        R.string.notification_error, it.message
                    )
                )
                Result.failure() // 6
            }

            else -> {
                sendNotification(context.getString(R.string.notification_upload_failed_unknown_error))
                Result.failure()
            }
        }
    }


    private suspend fun uploadVideo(uri: String?) {
        // Check if the file URI is provided
        if (uri == null)
            throw UploadingFailedException.FileNotFound()

        // Update notification to indicate file upload initiation
        sendNotification(context.getString(R.string.notification_uploading_file_message))

        // Simulate upload progress
        delay(1000L)
        for (i in 0..100) {
            delay(100)
            sendNotification(
                context.getString(
                    R.string.notification_uploading_file_in_progress,
                    i
                ) + "%"
            )
        }

        // Simulate delay for completion
        delay(1000L)

        // Update notification to indicate successful upload
        sendNotification(context.getString(R.string.notification_uploaded_successfully))
    }

    companion object {
        const val FILE_URI_TO_UPLOAD = "FILE_URI_TO_UPLOAD"
        const val UPLOAD_NOTIFICATION_CHANNEL_ID = "upload_channel"
        const val UPLOAD_NOTIFICATION_CHANNEL_NAME = "Cuju Video Upload"
    }

    fun sendNotification(message: String) {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val manager = getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        manager.createNotificationChannel(
            NotificationChannel(
                UPLOAD_NOTIFICATION_CHANNEL_ID,
                UPLOAD_NOTIFICATION_CHANNEL_NAME,
                NotificationManagerCompat.IMPORTANCE_DEFAULT
            )
        )
        manager.notify(
            1, NotificationCompat.Builder(context, UPLOAD_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.notification_uploading_file_title))
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(true)
                .build()
        )
    }
}

sealed class UploadingFailedException(override val message: String) : Exception(message) {
    class FileNotFound :
        UploadingFailedException(message = applicationContext.getString(R.string.notification_file_not_found_error))

    class Failed :
        UploadingFailedException(message = applicationContext.getString(R.string.notification_upload_file_failed_retry))
}
