package com.cuju.videoSdk.db.dao

import androidx.room.Dao
import com.cuju.data.BaseDao
import com.cuju.videoSdk.db.entities.VideoMetaData

@Dao
abstract class VideoMetaDataDao : BaseDao<VideoMetaData>()
