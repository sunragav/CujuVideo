package com.cuju.camera

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cuju.ui.components.CjScaffold
import com.cuju.video.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraHomeScreen(koinApplication: KoinApplication, onBackClick: () -> Unit = {}) {
    KoinIsolatedContext(context = koinApplication) {
        CjScaffold(
            title = stringResource(R.string.camera_home_record_video_title),
            onBackClick = onBackClick
        ) {
            val cameraAndRecordAudioPermissionState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.POST_NOTIFICATIONS,
                ),
            )
            if (cameraAndRecordAudioPermissionState.allPermissionsGranted) {
                CameraContent(
                    modifier = Modifier
                        .fillMaxSize(),
                )
            } else {
                CameraAndRecordAudioAndNotificationPermission(
                    onBackClick = onBackClick,
                    cameraAndRecordAudioPermissionState = cameraAndRecordAudioPermissionState
                )
            }
        }
    }
}
