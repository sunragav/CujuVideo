package com.cuju.videoSdk.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuju.videoSdk.db.dao.VideoMetaDataDao
import com.cuju.videoSdk.db.entities.VideoMetaData

@Database(
    entities = [
        VideoMetaData::class,
    ],
    version = 1,
)
abstract class VideoMetaDataDb : RoomDatabase() {
    abstract fun getVenueConfigurationDao(): VideoMetaDataDao
}
