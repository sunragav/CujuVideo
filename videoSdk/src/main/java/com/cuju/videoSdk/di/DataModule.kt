package com.cuju.videoSdk.di

import com.cuju.data.getRoomDatabase
import com.cuju.videoSdk.db.VideoMetaDataDb
import org.koin.dsl.module

private const val DB_FILE_NAME = "cdb.db"
fun videoMetaDataModule() = module {
    single<VideoMetaDataDb> { getRoomDatabase<VideoMetaDataDb>(DB_FILE_NAME) }
}
