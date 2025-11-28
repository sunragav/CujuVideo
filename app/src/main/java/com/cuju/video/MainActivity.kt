@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.cuju.video

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cuju.ui.components.CjDropDownMenu
import com.cuju.ui.components.CjLargeButton
import com.cuju.ui.theme.CujuVideoTheme
import com.cuju.videoplayer.VideoPlayer
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CujuVideoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        var expanded by remember { mutableStateOf(true) }
                        var selectedIndex by remember { mutableStateOf<Int?>(null) }
                        val appFolder: String = applicationContext.filesDir.absolutePath
                        val items = remember {
                            File(appFolder).listFiles().map { file -> file.absolutePath }
                                .filter { it.endsWith(".mp4") }
                        }

                        Box(Modifier.fillMaxWidth()) {
                            CjDropDownMenu(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = expanded,
                                items = items,
                                selectedIndex = selectedIndex,
                                onItemSelected = {
                                    selectedIndex = it
                                    expanded = false
                                },
                                onDismissRequest = {
                                    expanded = false
                                }
                            )
                            CjLargeButton(
                                text = "SELECT",
                                onClick = {
                                    expanded = true
                                })
                        }
                        selectedIndex?.let {
                            VideoPlayer(
                                uri = items[it]
                            )
                        }
                    }
                }
            }
        }
    }
}
