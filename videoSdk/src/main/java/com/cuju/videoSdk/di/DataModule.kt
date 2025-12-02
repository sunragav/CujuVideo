package com.cuju.videoSdk.di

import androidx.room.Room
import com.cuju.core.applicationContext
import com.cuju.core.coreModule
import com.cuju.videoSdk.db.VideoMetaDataDb
import com.cuju.videoSdk.repostories.LocalVideoMetaDataRepository
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import com.cuju.videoSdk.usecases.GetAllVideoMetaDataPaged
import com.cuju.videoSdk.usecases.GetThumbNailFileName
import com.cuju.videoSdk.usecases.GetVideoFileName
import com.cuju.videoSdk.usecases.GetVideoLifeCycleState
import com.cuju.videoSdk.usecases.GetVideoMetaDataListFromTheAppDirectory
import com.cuju.videoSdk.usecases.GetWorkerId
import com.cuju.videoSdk.usecases.InsertVideoMetaData
import com.cuju.videoSdk.usecases.PopulateDb
import com.cuju.videoSdk.usecases.PopulateVideoMetaDataDb
import com.cuju.videoSdk.usecases.UpdateUploadStatus
import com.cuju.videoSdk.usecases.UploadFile
import com.cuju.videoSdk.workmanager.PopulateVideoMetaDataWorker
import com.cuju.videoSdk.workmanager.UploadWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

private const val DB_FILE_NAME = "cdb.db"
fun videoMetaDataModule() = coreModule() +
        module {
            single<VideoMetaDataDb> {
                Room.databaseBuilder<VideoMetaDataDb>(
                    context = applicationContext,
                    name = DB_FILE_NAME,
                ).build()
            }
            single { get<VideoMetaDataDb>().videoMetaDataDao() }
            single<VideoMetaDataRepository> { LocalVideoMetaDataRepository(get(), get()) }
            workerOf(::PopulateVideoMetaDataWorker)
            workerOf(::UploadWorker)
            factoryOf(::UpdateUploadStatus)
            factoryOf(::GetThumbNailFileName)
            factoryOf(::GetVideoFileName)
            factoryOf(::GetVideoLifeCycleState)
            factoryOf(::GetWorkerId)
            factoryOf(::InsertVideoMetaData)
            factory { GetVideoMetaDataListFromTheAppDirectory(applicationContext) }
            factoryOf(::PopulateVideoMetaDataDb)
            factoryOf(::UploadFile)
            factoryOf(::GetAllVideoMetaDataPaged)
            factoryOf(::PopulateDb)
        }
