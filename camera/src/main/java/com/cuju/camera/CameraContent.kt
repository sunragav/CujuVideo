package com.cuju.camera

import android.os.Build
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuju.ui.ThumbnailHeight
import com.cuju.ui.ThumbnailWidth
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CameraContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val viewModel = koinViewModel<CameraViewModel> {
        val size: Size = density.run {
            Size(ThumbnailWidth.toPx().toInt(), ThumbnailHeight.toPx().toInt())
        }
        parametersOf(
            context.filesDir,
            size
        )
    }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.VIDEO_CAPTURE
            )
        }
    }
    val state by viewModel.captureState.collectAsStateWithLifecycle()
    Box(
        modifier = modifier
    ) {
        CameraPreviewContent(
            controller = controller,
            modifier = Modifier
                .fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShutterButton(
                captureMode = state.captureMode,
                onVideoRecordingStart = { viewModel.startRecording(controller, context) },
                onVideoRecordingFinish = viewModel::stopRecording
            )
        }
    }
}
