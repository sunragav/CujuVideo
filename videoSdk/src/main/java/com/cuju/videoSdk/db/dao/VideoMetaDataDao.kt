package com.cuju.videoSdk.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.cuju.data.BaseDao
import com.cuju.videoSdk.db.entities.VideoMetaData
import kotlinx.coroutines.flow.Flow

@Dao
abstract class VideoMetaDataDao : BaseDao<VideoMetaData>() {
    @Query("SELECT lifeCycleState FROM VideoMetaData WHERE videoUri = :uri")
    abstract fun getLifeCycleState(uri: String): Flow<String>

    @Query("SELECT * FROM VideoMetaData")
    abstract fun pagingSource(): PagingSource<Int, VideoMetaData>

    @Query("DELETE FROM VIDEOMETADATA WHERE videoUri NOT IN (:uriList)")
    abstract fun deleteItemNotInIds(uriList: Array<String>)
}
