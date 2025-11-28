package com.cuju.explorer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cuju.ui.HalfMargin
import com.cuju.ui.Margin
import com.cuju.ui.SmallMargin
import com.cuju.ui.components.CjElevatedButton
import com.cuju.ui.components.CjLabelMedium
import com.cuju.ui.components.CjLabelSmall
import com.cuju.ui.components.CjVerticalScrollbar
import com.cuju.ui.theme.CujuVideoTheme
import io.github.oikvpqya.compose.fastscroller.rememberScrollbarAdapter
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CujuGalleryScreen(koinApplication: KoinApplication) {
    KoinIsolatedContext(context = koinApplication) {
        val context = LocalContext.current
        val viewModel = koinViewModel<CujuGalleryViewModel> {
            parametersOf(context.filesDir.absolutePath)
        }
        val files = viewModel.files.collectAsStateWithLifecycle()

        Box(
            Modifier
                .fillMaxSize()
                .padding(HalfMargin)
        ) {
            val state = rememberLazyGridState()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Margin),
                state = state,
                columns = GridCells.Adaptive(ThumbNailSize),
                horizontalArrangement = Arrangement.spacedBy(SmallMargin),
                verticalArrangement = Arrangement.spacedBy(SmallMargin)
            ) {
                items(files.value) { path ->
                    CjElevatedButton(onClick = {}) {
                        CjLabelSmall(
                            modifier = Modifier.padding(bottom = SmallMargin),
                            text = path.fileName.toString()
                        )
                        CjLabelMedium(
                            text = "UPLOADING",
                            color = CujuVideoTheme.colorScheme.contentPositive
                        )
                    }
                }
            }
            CjVerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = state),
            )
        }
    }
}
