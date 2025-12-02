package com.cuju.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuju.videoSdk.usecases.GetThumbNailFileName
import com.cuju.videoSdk.usecases.GetVideoFileName
import com.cuju.videoSdk.usecases.InsertVideoMetaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.lang.System.currentTimeMillis

class CameraViewModel(
    private val filesDir: File,
    private val thumbnailSize: Size,
    private val getVideoFileName: GetVideoFileName,
    private val getThumbNailFileName: GetThumbNailFileName,
    private val insertVideoMetaData: InsertVideoMetaData,
) : ViewModel() {
    val captureState = MutableStateFlow(VideoCaptureState())
    private var recording: Recording? = null

    fun stopRecording() {
        if (recording != null) {
            recording?.stop()
            recording = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    fun startRecording(controller: LifecycleCameraController, context: Context) {
        captureState.update {
            it.copy(error = null, captureMode = CaptureMode.VIDEO_RECORDING)
        }
        val timeMillis = currentTimeMillis()
        val videoFileName = getVideoFileName(timeMillis)

        val videoOutputFile = File(
            filesDir, videoFileName
        )
        recording = controller.startRecording(
            FileOutputOptions.Builder(videoOutputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(context),
        ) { event ->
            when (event) {
                is VideoRecordEvent.Finalize -> {
                    if (event.hasError()) {
                        recording?.close()
                        recording = null
                        if (videoOutputFile.exists()) {
                            videoOutputFile.delete()
                        }
                        captureState.update {
                            it.copy(
                                error = Error.VideoRecordingFailedError(
                                    event.cause
                                ),
                                captureMode = CaptureMode.VIDEO_READY
                            )
                        }
                    } else {
                        saveThumbnail(
                            timeMillis,
                            videoOutputFile,
                            File(context.filesDir, getThumbNailFileName(timeMillis))
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveThumbnail(timeStamp: Long, videoOutputFile: File, destinationFile: File) =
        viewModelScope.launch {
            try {
                val thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(
                    videoOutputFile,
                    thumbnailSize,
                    null
                )
                val fileOutputStream = FileOutputStream(destinationFile)
                thumbnailBitmap.compress(Bitmap.CompressFormat.PNG, 75, fileOutputStream)
                fileOutputStream.close()
                insertVideoMetaData(
                    videoOutputFile = videoOutputFile.absolutePath.toString(),
                    thumbnailFile = destinationFile.absolutePath.toString(),
                    timeStamp = timeStamp,
                    fileName = videoOutputFile.name
                )
                captureState.update {
                    it.copy(error = null, captureMode = CaptureMode.VIDEO_READY)
                }
            } catch (e: Exception) {
                if (videoOutputFile.exists()) {
                    videoOutputFile.delete()
                }
                if (destinationFile.exists()) {
                    destinationFile.delete()
                }
                captureState.update {
                    it.copy(error = Error.ThumbnailGenerationFailedError(e))
                }
            }
        }

}

enum class CaptureMode {
    VIDEO_READY,
    VIDEO_RECORDING,
}

sealed class Error(open val error: Throwable?) {
    data class VideoRecordingFailedError(override val error: Throwable?) : Error(error = error)
    data class ThumbnailGenerationFailedError(override val error: Throwable?) : Error(error = error)
}

data class VideoCaptureState(
    val error: Error? = null,
    val captureMode: CaptureMode = CaptureMode.VIDEO_READY
)
