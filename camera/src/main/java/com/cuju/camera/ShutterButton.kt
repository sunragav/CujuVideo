package com.cuju.camera

import androidx.compose.runtime.Composable
import com.cuju.ui.components.CjCircleButton
import com.cuju.ui.components.CjSquareButton

@Composable
fun ShutterButton(
    captureMode: CaptureMode,
    onVideoRecordingStart: () -> Unit,
    onVideoRecordingFinish: () -> Unit,
) {
    if (captureMode == CaptureMode.VIDEO_READY) {
        CjCircleButton(
            onClick = onVideoRecordingStart,
        )
    } else if (captureMode == CaptureMode.VIDEO_RECORDING) {
        CjSquareButton(
            onClick = onVideoRecordingFinish,
        )
    }
}
