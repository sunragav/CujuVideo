package com.cuju.explorer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.rememberAsyncImagePainter
import com.cuju.ui.HalfMargin
import com.cuju.ui.Margin
import com.cuju.ui.SmallMargin
import com.cuju.ui.components.CjElevatedButton
import com.cuju.ui.components.CjLabelSmall
import com.cuju.ui.components.CjScaffold
import com.cuju.ui.components.CjVerticalScrollbar
import com.cuju.video.R
import com.cuju.videoSdk.component.VideoLifeCycleContent
import com.cuju.videoSdk.domain.models.VideoMetaData
import io.github.oikvpqya.compose.fastscroller.rememberScrollbarAdapter
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinIsolatedContext
import org.koin.core.KoinApplication
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CujuGalleryScreen(onItemClick: (VideoMetaData) -> Unit = {}) {
    val viewModel = koinViewModel<CujuGalleryViewModel>()
    val items = viewModel.videoMetaDataList.collectAsLazyPagingItems()
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
            items(
                count = items.itemCount,
                key = items.itemKey { item -> item.videoUri },
                contentType = items.itemContentType { "VideoMetaData" }
            ) { index: Int ->
                val item = items[index]
                item?.let {
                    CjElevatedButton(onClick = { onItemClick(item) }) {
                        Image(
                            painter = rememberAsyncImagePainter(File(it.thumbNailUri)),
                            contentDescription = null
                        )
                        CjLabelSmall(
                            modifier = Modifier.padding(bottom = SmallMargin),
                            text = it.fileName
                        )
                        CjLabelSmall(
                            modifier = Modifier.padding(bottom = SmallMargin),
                            text = it.timeStamp
                        )
                        VideoLifeCycleContent(state = it.lifeCycleState)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CujuGalleryHomeScreen(
    koinApplication: KoinApplication,
    onItemClick: (VideoMetaData) -> Unit,
    onBackClick: () -> Unit = {}
) {
    KoinIsolatedContext(context = koinApplication) {
        CjScaffold(
            title = stringResource(R.string.gallery_home_screen_title),
            onBackClick = onBackClick
        ) {
            CujuGalleryScreen(onItemClick = onItemClick)
        }
    }
}
