package com.cuju.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresPermission
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.lifecycle.LifecycleOwner
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Locale

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

data class Media(var uri: Uri, var mediaType: MediaType)
enum class MediaType {
    VIDEO,
}

enum class CaptureMode {
    VIDEO_READY,
    VIDEO_RECORDING,
}

@Composable
fun CujuCameraScreen() {
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_FRONT) }
    var captureMode by remember { mutableStateOf(CaptureMode.VIDEO_READY) }
    var recording by remember { mutableStateOf<Recording?>(null) }
    val recorder = remember {
        Recorder.Builder()
            .setAspectRatio(AspectRatio.RATIO_16_9)
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
            .build()
    }
    val videoCaptureUseCase = remember { VideoCapture.Builder(recorder).build() }

    val localContext = LocalContext.current


    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startVideoCapture(context: Context, onMediaCaptured: (Media) -> Unit) {
        val name = "Cuju-video-recording-" +
                SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(currentTimeMillis()) + ".mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/cuju-video-recordings")
            }
        }
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(
            context.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        )
            .setContentValues(contentValues)
            .build()

        val captureListener = Consumer<VideoRecordEvent> { event ->
//            recordingState = event
            if (event is VideoRecordEvent.Finalize) {
                onMediaCaptured(Media(event.outputResults.outputUri, MediaType.VIDEO))
            }
        }

        // configure Recorder and Start recording to the mediaStoreOutput.
        recording = videoCaptureUseCase.output
            .prepareRecording(context, mediaStoreOutput)
            .apply { withAudioEnabled() }
            .start(ContextCompat.getMainExecutor(context), captureListener)
    }

    @SuppressLint("MissingPermission")
    fun onVideoRecordingStart() {
        captureMode = CaptureMode.VIDEO_RECORDING
        startVideoCapture(localContext, { capturedMedia: Media? ->
            println("Captured video: " + capturedMedia?.uri.toString())
        })
    }

    fun onVideoRecordingFinish() {
        captureMode = CaptureMode.VIDEO_READY
        recording?.stop()
        recording = null
    }

    Box {
        CameraPreview(
            lensFacing = lensFacing,
            videoCaptureUseCase = videoCaptureUseCase
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            /*   Row {
                   Button(onClick = { lensFacing = CameraSelector.LENS_FACING_FRONT }) {
                       Text("Front camera")
                   }
                   Button(onClick = { lensFacing = CameraSelector.LENS_FACING_BACK }) {
                       Text("Back camera")
                   }
               }*/
            ShutterButton(
                captureMode = captureMode,
                onVideoRecordingStart = {
                    captureMode = CaptureMode.VIDEO_RECORDING
                    onVideoRecordingStart()
                },
                onVideoRecordingFinish = {
                    captureMode = CaptureMode.VIDEO_READY
                    onVideoRecordingFinish()
                }
            )
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    lensFacing: Int,
    videoCaptureUseCase: VideoCapture<Recorder>
) {
    val previewUseCase = remember { androidx.camera.core.Preview.Builder().build() }

    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    var cameraControl by remember { mutableStateOf<CameraControl?>(null) }

    val localContext = LocalContext.current

    fun rebindCameraProvider() {
        cameraProvider?.let { cameraProvider ->
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                localContext as LifecycleOwner,
                cameraSelector,
                previewUseCase, videoCaptureUseCase
            )
            cameraControl = camera.cameraControl
        }
    }

    LaunchedEffect(Unit) {
        cameraProvider = ProcessCameraProvider.awaitInstance(localContext)
        rebindCameraProvider()
    }

    LaunchedEffect(lensFacing) {
        rebindCameraProvider()
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            PreviewView(context).also {
                previewUseCase.surfaceProvider = it.surfaceProvider
                rebindCameraProvider()
            }
        }
    )
}

@Composable
fun ShutterButton(
    captureMode: CaptureMode,
    onVideoRecordingStart: () -> Unit,
    onVideoRecordingFinish: () -> Unit,
) {
    Box(modifier = Modifier.padding(25.dp, 0.dp)) {
        if (captureMode == CaptureMode.VIDEO_READY) {
            Button(
                onClick = onVideoRecordingStart,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp),
            ) {}
        } else if (captureMode == CaptureMode.VIDEO_RECORDING) {
            Button(
                onClick = onVideoRecordingFinish,
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
            ) {}
            Spacer(modifier = Modifier.width(100.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CujuCameraScreenPreview() {
    CujuCameraScreen()
}
