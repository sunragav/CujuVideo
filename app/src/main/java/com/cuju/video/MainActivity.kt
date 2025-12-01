package com.cuju.video

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cuju.camera.CameraModule
import com.cuju.explorer.CujuGalleryModule
import com.cuju.ui.components.CjLargeButton
import com.cuju.ui.theme.CujuVideoTheme
import com.cuju.videoplayer.CujuVideoPlayerModule
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Camera

@Serializable
object Gallery

@Serializable
data class Player(val videoUri: String)
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CujuVideoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = Home) {
                            composable<Home> {
                                Home(
                                    onStartCameraClick = {
                                        navController.navigate(Camera)
                                    },
                                    onGalleryClick = {
                                        navController.navigate(Gallery)
                                    }
                                )
                            }
                            composable<Camera> {
                                val cameraModule = CameraModule()
                                cameraModule.CujuCameraHomeScreen {
                                    navController.popBackStack()
                                }
                            }

                            composable<Gallery> {
                                val cujuGalleryModule = CujuGalleryModule()
                                cujuGalleryModule.CujuGalleryHomeScreen(onItemClick = {
                                    navController.popBackStack()
                                    navController.navigate(Player(it.videoUri))
                                }) {
                                    navController.popBackStack()
                                }
                            }
                            composable<Player> {
                                val cujuVideoPlayerModule = CujuVideoPlayerModule()
                                val playerRoute: Player = it.toRoute<Player>()
                                cujuVideoPlayerModule.CujuVideoPlayerScreen(
                                    uri = playerRoute.videoUri
                                ) {
                                    navController.popBackStack()
                                    navController.navigate(Gallery)
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Home(
    onStartCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CjLargeButton(
            onClick = onStartCameraClick,
            text = stringResource(R.string.home_screen_start_camera_action)
        )
        CjLargeButton(
            onClick = onGalleryClick,
            text = stringResource(R.string.home_screen_gallery_action)
        )
    }
}
