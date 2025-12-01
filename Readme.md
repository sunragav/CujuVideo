# Cuju Video Project

Cuju is an Android application designed for capturing, processing, and managing video content. It
features a modular architecture with a dedicated Camera and Video SDK, leveraging modern Android
development practices and libraries.

## 🌟 Features

- **Video Recording**: In-app camera functionality to capture video.
- **Background Video Processing**: Uses `WorkManager` to reliably process video files in the
  background, including metadata population.
- **Modern Android Stack**: Built with Kotlin, Jetpack Compose, and modern libraries.
- **Dependency Injection**: Utilizes Koin for managing dependencies across the application.
- **Navigation**: Compose type safe navigation for seamless UI transitions.
- **Pagination**: Paging 3 for loading the stored meta data from the DB gracefully.
- **Modular Architecture**: Separates concerns into distinct modules like `:app`, `:camera`,
  `explorer`, `videoplayer` and `:videoSdk`.

## 🛠️ Tech Stack & Libraries

- **Core**: Kotlin
- **UI**: Jetpack Compose
- **Asynchronous Programming**: Kotlin Coroutines
- **Camera**: CameraX
- **Dependency Injection**: Koin
- **Background Processing**: WorkManager
- **Build System**: Gradle with Kotlin DSL and Version Catalogs

## 🏗️ Project Structure

The project is divided into several modules to promote separation of concerns and scalability:

- `app`: The main application module that integrates all other modules and provides the user-facing
  UI.
- `camera`: A dedicated module for all camera-related functionalities, including the camera screen
  and video capture logic.
- `explorer`: A dedicated module for displaying the gallery of the recorded videos
- `videoplayer`: A dedicated module for displaying the video details with a player to play the file
  and a button to upload the file
- `videoSdk`: A Software Development Kit module responsible for handling video processing, metadata
  extraction, and other video-related background tasks.

## 🚀 Getting Started

Follow these instructions to get the project up and running on your local machine for development
and testing purposes.

### Prerequisites

- Android Studio (latest stable version recommended)
- Android SDK
- JDK 17 or higher
  I used Android Studio Otter | 2025.2.1 at the time of development.

### Translations

The keys are available in lokalise tool which can be used to manage the translation of the texts in
various languages
There is a dedicated lokalise convention plugin which adds the downloadStrings task to the library
modules(projects).

### Detekt and Lint check

There is a Detekt convention plugin which adds the detekt gradle tasks at the verification stage.

Detekt is a powerful static analysis tool for kotlin.

### Important parts

The camera module records videos and thumbnails in the application local directory `files` where no
special permission is needed
The videoSdk module has some useful use cases that contains the domain logic used by the other
feature modules.
GetVideoMetaDataListFromTheAppDirectory is a use case that monitors the application local directory
for changes.

```kotlin
class GetVideoMetaDataListFromTheAppDirectory(
    private val context: Context,
) {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    operator fun invoke(): Flow<List<VideoMetaData>> {

        val path = context.filesDir.absolutePath
        return Path.of(path).listDirectoryEntriesFlow("*.$CUJU_VIDEO_FORMAT").map {
            it.map(PathMapper::map)
        }
    }
}
```

Any addition or deletion of file in the application folder `files` directory, will be immediately
caught by the watch
The meta data like `timestamp` absolute path of video file and the thumbnail file are then stored in
the room db VideoMetaDataDb in VideoMetaData table.

```kotlin
class PopulateVideoMetaDataDb(
    private val getVideoMetaDataListFromTheAppDirectory: GetVideoMetaDataListFromTheAppDirectory,
    private val videoMetaDataRepository: VideoMetaDataRepository
) {
    suspend operator fun invoke() =
        getVideoMetaDataListFromTheAppDirectory().collect { videoMetaData ->
            videoMetaDataRepository.deleteAllVideoMetaDataNotInTheList(videoMetaData.map { it.videoUri })
            videoMetaDataRepository.insertVideoMetaDataOrIgnore(
                videoMetaData.map(
                    VideoMetaDataDomainToEntityMapper::map
                )
            )
        }
}
```

Since this can be time consuming based on the number of files in this folder, a worker has been
created.

```kotlin
class PopulateVideoMetaDataWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val populateVideoMetaDataDb: PopulateVideoMetaDataDb,
) : CoroutineWorker(context, workerParams), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                populateVideoMetaDataDb()
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    companion object Companion {
        const val POPULATE_VIDEO_META_DATA_SUCCESS = "POPULATE_VIDEO_META_DATA_SUCCESS"
        const val POPULATE_VIDEO_META_DATA_INPUT_DATA = "POPULATE_VIDEO_META_DATA_INPUT_DATA"
    }
}
```

The use case `PopulateDb` trigger this worker from the GalleryViewModel

```kotlin
class PopulateDb {
    operator fun invoke() {
        val uuid = UUID.randomUUID()
        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<PopulateVideoMetaDataWorker>()
                .setInputData(
                    Data.Builder().putString(
                        PopulateVideoMetaDataWorker.POPULATE_VIDEO_META_DATA_INPUT_DATA,
                        ""
                    ).build()
                ).setId(uuid)
                .build()
        )
    }
}
```

The Gallery Screen defined in the `gallery` module loads this meta data from room in a paginated
way,
so that the files are loaded lazily in a graceful way.

On clicking any item in the gallery, the player view is launched which will automatically start
playing the video file.

The `videoSdk` module not only contain the use cases, but also the repository layer which is
implemented via RoomDb

One could also find a fake worker that simulates uploading the video file. The uploading of the
files can be triggered from the player view.
The uploading worker is queued to the work manager and will be executed only when the battery level
is good.

```kotlin
class UploadFile(private val videoMetaDataRepository: VideoMetaDataRepository) {
    suspend operator fun invoke(uri: String) {
        val metadata = videoMetaDataRepository.getVideoMetaDataByUri(uri)
        val uuid = UUID.randomUUID()
        val powerConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(
            OneTimeWorkRequestBuilder<UploadWorker>().setConstraints(powerConstraints)
                .setInputData(
                    Data.Builder().putString(UploadWorker.FILE_URI_TO_UPLOAD, uri).build()
                )
                .setId(uuid)
                .build()
        )

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdFlow(uuid)
            .collect { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED,
                        WorkInfo.State.RUNNING -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.UPLOADING
                        )

                        WorkInfo.State.SUCCEEDED -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.UPLOADED
                        )

                        WorkInfo.State.BLOCKED,
                        WorkInfo.State.CANCELLED,
                        WorkInfo.State.FAILED -> updateLifeCycleState(
                            metadata = metadata,
                            VideoLifeCycle.RECORDED
                        )

                    }
                }
            }
    }
}
```
