package com.cuju.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cuju.ui.LargeMargin
import com.cuju.ui.Margin
import com.cuju.ui.components.CjLabelMedium
import com.cuju.ui.components.CjLabelSmall
import com.cuju.video.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraAndRecordAudioAndNotificationPermission(
    modifier: Modifier = Modifier,
    cameraAndRecordAudioPermissionState: MultiplePermissionsState,
    onBackClick: () -> Unit = {},
) {
    var alreadyRequestedCameraPermissions by remember { mutableStateOf(false) }
    fun onRequestPermissionsClicked() {
        cameraAndRecordAudioPermissionState.launchMultiplePermissionRequest()
        alreadyRequestedCameraPermissions = true
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(Margin),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Margin),
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Camera Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(LargeMargin),
                )
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Microphone Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(LargeMargin),
                )
            }

            Row {
                CjLabelMedium(text = stringResource(R.string.camera_permission_rationale))
            }
            if (alreadyRequestedCameraPermissions) {
                Row {
                    CjLabelSmall(text = stringResource(R.string.camera_permission_settings))
                }
            } else {
                if (cameraAndRecordAudioPermissionState.shouldShowRationale) {
                    Row {
                        Button(onClick = ::onRequestPermissionsClicked) {
                            CjLabelMedium(text = stringResource(R.string.grant_permission))
                        }
                    }
                } else {
                    LaunchedEffect(cameraAndRecordAudioPermissionState) {
                        cameraAndRecordAudioPermissionState.launchMultiplePermissionRequest()
                    }
                    alreadyRequestedCameraPermissions = true
                }
            }

            Button(onClick = { onBackClick() }) {
                CjLabelMedium(text = stringResource(R.string.back))
            }
        }
    }
}
