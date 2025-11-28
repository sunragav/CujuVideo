package com.cuju.core

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runInterruptible
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import kotlin.io.path.listDirectoryEntries

/**
 * Creates a flow WatchEvent from a watchService
 */
@RequiresApi(Build.VERSION_CODES.O)
fun WatchService.eventFlow(): Flow<List<WatchEvent<out Any>>> = flow {
    while (currentCoroutineContext().isActive) {
        coroutineScope {
            var key: WatchKey? = null
            val job = launch {
                runInterruptible(Dispatchers.IO) {
                    key = take()
                }
            }
            job.join()
            val currentKey = key
            if (currentKey != null) {
                emit(currentKey.pollEvents())
                currentKey.reset()
            }
        }
    }
}

/**
 * Returns a flow with the files inside a folder (with a given glob)
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Path.listDirectoryEntriesFlow(glob: String = "*"): Flow<List<Path>> {
    val watchService = watch()
    return watchService.eventFlow()
        .map { listDirectoryEntries(glob) }
        .onStart { emit(listDirectoryEntries(glob)) }
        .onCompletion { watchService.close() }
        .flowOn(Dispatchers.IO)
}

/**
 * Creates a new WatchService for any Event
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Path.watch(): WatchService {
    return watch(
        StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_DELETE
    )
}

/**
 * Creates a new watch service
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Path.watch(vararg events: WatchEvent.Kind<out Any>) =
    fileSystem.newWatchService()!!.apply { register(this, events) }
