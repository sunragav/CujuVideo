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


    @Query("UPDATE VideoMetaData SET lifeCycleState=:lifeCycleState WHERE videoUri = :videoUri")
    abstract fun updateLifeCycleData(videoUri: String, lifeCycleState: String)

    @Query("SELECT * FROM VideoMetaData")
    abstract fun pagingSource(): PagingSource<Int, VideoMetaData>

    @Query("DELETE FROM VIDEOMETADATA WHERE videoUri NOT IN (:uriList)")
    abstract fun deleteItemNotInIds(uriList: Array<String>)

    @Query("UPDATE VideoMetaData SET workerId=:workedId WHERE videoUri = :videoUri")
    abstract fun updateWorkerId(videoUri: String, workedId: String)

    @Query("SELECT workerId FROM VideoMetaData WHERE videoUri = :uri")
    abstract fun getWorkerId(uri: String): Flow<String?>
}
